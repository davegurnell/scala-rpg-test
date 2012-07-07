package scalaquest.asset

case class Tile(val x: Int, val y: Int)

object Tile {
  val size = 16 // pixels
  val image = ImageLibrary.load("tilesheet")

  val grass    = Tile(18, 8)
  val pavement = Tile(15, 17)
}
