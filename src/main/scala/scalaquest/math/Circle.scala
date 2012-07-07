package scalaquest.math

import scala.math._

import scalaquest.util._

case class Circle(val origin: Vec, val radius: Double) extends Area {
  def x = origin.x
  def y = origin.y
  def w = 2 * radius
  def h = 2 * radius

  def +(v: Vec) = Circle(origin + v, radius)
  def -(v: Vec) = Circle(origin - v, radius)

  def contains(v: Vec) = (origin - v).length <= radius
}
