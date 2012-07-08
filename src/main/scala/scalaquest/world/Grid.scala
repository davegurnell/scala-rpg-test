package scalaquest.world

import scala.collection._
import scalaquest.math._

trait Grid[T] {
  def get(x: Int, y: Int): T

  def get(pos: Vec): T = {
    get(pos.x.toInt, pos.y.toInt)
  }
}
