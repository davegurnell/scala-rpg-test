package scalaquest.math

import scala.math._

import scalaquest.util._

case class Matrix(
  val _00: Double, val _10: Double, val _20: Double,
  val _01: Double, val _11: Double, val _21: Double,
  val _02: Double, val _12: Double, val _22: Double
) {
  lazy val position = Vec(_20 / _22, _21 / _22)
  lazy val rotation = math.acos(_00 / _22)
  lazy val xScale = _00 / _22
  lazy val yScale = _11 / _22

  def unary_- : Matrix = Matrix(
    -this._00, -this._10, -this._20,
    -this._01, -this._11, -this._21,
    -this._02, -this._12, -this._22
  )

  def +(that: Matrix): Matrix = Matrix(
    this._00 + that._00,
    this._10 + that._10,
    this._20 + that._20,
    this._01 + that._01,
    this._11 + that._11,
    this._21 + that._21,
    this._02 + that._02,
    this._12 + that._12,
    this._22 + that._22
  )

  def -(that: Matrix): Matrix = Matrix(
    this._00 - that._00,
    this._10 - that._10,
    this._20 - that._20,
    this._01 - that._01,
    this._11 - that._11,
    this._21 - that._21,
    this._02 - that._02,
    this._12 - that._12,
    this._22 - that._22
  )

  def *(that: Matrix): Matrix = new Matrix(
    this._00 * that._00 + this._10 * that._01 + this._20 * that._02,
    this._00 * that._10 + this._10 * that._11 + this._20 * that._12,
    this._00 * that._20 + this._10 * that._21 + this._20 * that._22,
    this._01 * that._00 + this._11 * that._01 + this._21 * that._02,
    this._01 * that._10 + this._11 * that._11 + this._21 * that._12,
    this._01 * that._20 + this._11 * that._21 + this._21 * that._22,
    this._02 * that._00 + this._12 * that._01 + this._22 * that._02,
    this._02 * that._10 + this._12 * that._11 + this._22 * that._12,
    this._02 * that._20 + this._12 * that._21 + this._22 * that._22
  )

  def *(v: Vec): Vec = Vec(
    this._00 * v.x + this._10 * v.y + this._20 * v.k,
    this._01 * v.x + this._11 * v.y + this._21 * v.k,
    this._02 * v.x + this._12 * v.y + this._22 * v.k
  )

  def *(box: AxisBox): AxisBox = AxisBox(this * box.p0, this * box.p1)

  def *(d: Double): Matrix = new Matrix(
    this._00 * d, this._10 * d, this._20 * d,
    this._01 * d, this._11 * d, this._21 * d,
    this._02 * d, this._12 * d, this._22 * d
  )

  def /(d: Double): Matrix = new Matrix(
    this._00 / d, this._10 / d, this._20 / d,
    this._01 / d, this._11 / d, this._21 / d,
    this._02 / d, this._12 / d, this._22 / d
  )

  lazy val normalized: Boolean = _22 == 1

  def normalize: Matrix = {
    if (normalized) {
      this
    } else new Matrix(
      _00 / _22, _10 / _22, _20 / _22,
      _01 / _22, _11 / _22, _21 / _22,
      _02 / _22, _12 / _22, _22 / _22
    )
  }

  def determinant: Double =
    _00 * _11 * _22 -
    _00 * _12 * _21 -
    _01 * _10 * _22 +
    _01 * _12 * _20 +
    _02 * _10 * _21 -
    _02 * _11 * _20

  def transpose: Matrix = Matrix(
    _00, _01, _02,
    _10, _11, _12,
    _20, _21, _22
  )

  def inverse: Matrix = Matrix(
    _11 * _22 - _12 * _21,
    _02 * _21 - _01 * _22,
    _01 * _12 - _02 * _11,
    _12 * _20 - _10 * _22,
    _00 * _22 - _02 * _20,
    _02 * _10 - _00 * _12,
    _10 * _21 - _11 * _20,
    _01 * _20 - _00 * _21,
    _00 * _11 - _01 * _10
  ) / this.determinant

  def toList: List[Double] =
    List(_00, _10, _20, _01, _11, _21, _02, _12, _22)

  override def equals(other: Any): Boolean = other match {
    case m: Matrix =>
      _00 == m._00 && _10 == m._10 && _20 == m._20 &&
      _01 == m._01 && _11 == m._11 && _21 == m._21 &&
      _02 == m._02 && _12 == m._12 && _22 == m._22
    case _ => false
  }

  override def toString: String =
    "Matrix(" + toList.map(_.toString).reduceLeft(_ + ", " + _) + ")"
}

object Matrix {
  val identity = Matrix(1, 0, 0, 0, 1, 0, 0 ,0 ,1)
  def scaling(s: Double): Matrix = Matrix(s, 0, 0, 0, s, 0, 0, 0, 1)
  def scaling(x: Double, y: Double): Matrix = Matrix(x, 0, 0, 0, y, 0, 0, 0, 1)
  def rotation(a: Double): Matrix = {
    val sina = sin(a)
    val cosa = cos(a)
    Matrix(cosa, -sina, 0, sina, cosa, 0, 0, 0, 1)
  }
  def translation(x: Double, y: Double): Matrix = Matrix(1, 0, x, 0, 1, y, 0, 0, 1)
  def translation(v: Vec): Matrix = Matrix(1, 0, v.x, 0, 1, v.y, 0, 0, 1)
}
