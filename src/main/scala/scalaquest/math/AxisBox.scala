package scalaquest.math

import scala.math._

import scalaquest.util._

case class AxisBox(val a: Vec, val b: Vec) extends Area {
  lazy val p0 = Vec(min(a.x, b.x), min(a.y, b.y))
  lazy val p1 = Vec(max(a.x, b.x), max(a.y, b.y))

  def x0 = p0.x
  def y0 = p0.y
  def x1 = p1.x
  def y1 = p1.y

  def cx = (p0.x + p1.x) / 2
  def cy = (p0.y + p1.y) / 2
  def w = p1.x - p0.x
  def h = p1.y - p0.y

  def +(v: Vec) = AxisBox(a + v, b + v)
  def -(v: Vec) = AxisBox(a - v, b - v)

  def randomPoint = Vec(
    x0 + math.random * (x1 - x0),
    y0 + math.random * (y1 - y0))

  def contains(p: Vec) = x0 <= p.x && x1 >= p.x &&
               y0 <= p.y && y1 >= p.y

  // Returns a vector from p to the nearest point inside the box:
  def clipVec(p: Vec): Vec = {
    val dx = if(x0 > p.x) { x0 - p.x } else if(x1 < p.x) { x1 - p.x } else { 0.0 }
    val dy = if(y0 > p.y) { y0 - p.y } else if(y1 < p.y) { y1 - p.y } else { 0.0 }
    Vec(dx, dy)
  }

  def clip(p: Vec) = Vec(
    math.max(x0, math.min(x1, p.x)),
    math.max(y0, math.min(y1, p.y)))
}

object AxisBox {

  def apply(x0: Double, y0: Double, x1: Double, y1: Double): AxisBox =
    AxisBox(Vec(x0, y0), Vec(x1, y1))

  def xywh(x: Double, y: Double, w: Double, h: Double): AxisBox =
    AxisBox(Vec(x, y), Vec(x + w, y + h))

  val zero = AxisBox.centered(0.0)

  def union(boxes: Seq[AxisBox]) = {
    if(boxes.isEmpty) {
      AxisBox.zero
    } else {
      var x0 = boxes.head.p0.x
      var y0 = boxes.head.p0.y
      var x1 = boxes.head.p1.x
      var y1 = boxes.head.p1.y

      boxes.foreach { box =>
        x0 = math.min(x0, box.p0.x)
        y0 = math.min(y0, box.p0.y)
        x1 = math.max(x1, box.p1.x)
        y1 = math.max(y1, box.p1.y)
      }

      AxisBox(Vec(x0, y0), Vec(x1, y1))
    }
  }

  def centered(size: Double): AxisBox = centered(size, size)
  def centered(width: Double, height: Double): AxisBox =
    AxisBox(Vec(-width/2, -height/2), Vec(width/2, height/2))

  def atOrigin(size: Double): AxisBox = atOrigin(size, size)
  def atOrigin(width: Double, height: Double): AxisBox =
    AxisBox(Vec(0, 0), Vec(width, height))
}
