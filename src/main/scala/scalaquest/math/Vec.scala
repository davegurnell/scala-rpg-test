package scalaquest.math

import scala.math._
import scalaquest.util._

case class Vec(x: Double, y: Double) {
  // The third homogeneous coordinate - used in the calculations in Matrix.
  val k = 1.0

  def this(x: Double, y: Double, k: Double) = this(x/k, y/k)

  def unary_- : Vec = Vec(-x, -y)

  def +(that: Vec): Vec = Vec(this.x + that.x, this.y + that.y)
  def -(that: Vec): Vec = Vec(this.x - that.x, this.y - that.y)

  def *(d: Double): Vec = Vec(x*d, y*d)
  def /(d: Double): Vec = Vec(x/d, y/d)

  def squaredLength = x*x + y*y
  def length = sqrt(x*x + y*y)
  def isZeroLength = (x == 0) && (y == 0)

  def normalize: Vec = {
    def len = length
    if(len == 0) Vec(1, 0) else this / len
  }

  def length(len: Double): Vec = this.normalize * len

  def trim(len: Double): Vec =
    if(this.length <= len) this else this.normalize * len

  def extend(len: Double): Vec =
    if(this.length >= len) this else this.normalize * len

  def floor: Vec = Vec(x.floor, y.floor)

  def ceil: Vec = Vec(x.ceil, y.ceil)

  def round: Vec = Vec(x.round, y.round)

  def clip(box: AxisBox) =
    Vec(min(max(x, box.x0), box.x1),
      min(max(y, box.y0), box.y1))

  def wrap(box: AxisBox) = {
    var newX = x
    var newY = y
    while(newX <  box.x0) newX += box.w
    while(newX >= box.x1) newX -= box.w
    while(newY <  box.y0) newY += box.h
    while(newY >= box.y1) newY -= box.h
    Vec(newX, newY)
  }

  def angle: Double = math.atan2(y, x)

  def rotate(by: Double) = {
    val angle = this.angle
    val len = this.length
    Vec.polar(angle + by, len)
  }

  def left = Vec(-y, x)
  def right = Vec(y, -x)

  def dot(that: Vec): Double = this.x * that.x + this.y * that.y
  def cross(that: Vec): Double = this.x * that.y - this.y * that.x
}

object Vec {
  def apply(x: Double, y: Double, k: Double) = new Vec(x/k, y/k)

  implicit def tupleToVec(t: (Double, Double)) = Vec(t._1, t._2, 1)

  val zero = new Vec(0, 0)

  def random: Vec = random(1, 1)
  def random(l: Double): Vec = random(l, l)
  def random(x: Double, y: Double): Vec = {
    Vec(x * math.random - x/2, y * math.random - y/2)
  }

  def polar(angle: Double): Vec = polar(angle, 1.0)
  def polar(angle: Double, len: Double): Vec =
    Vec(len * math.cos(angle), len * math.sin(angle))
}
