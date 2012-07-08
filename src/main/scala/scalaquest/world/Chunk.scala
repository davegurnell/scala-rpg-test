package scalaquest.world

import scala.collection.mutable
import scalaquest.asset._
import scalaquest.math._

/**
 * Create with a flattened sequence of size x size tiles.
 *
 * Tiles should be stored in top->bottom, left-> right order.
 */
case class Chunk(val data: mutable.Seq[Tile]) extends Grid[Tile] {
  if(data.length != Chunk.size * Chunk.size) {
    throw new Exception("Chunk generated with the wrong number of tiles: " + data.length)
  }

  def get(x: Int, y: Int): Tile = {
    data(y * Chunk.size + x)
  }
}

object Chunk {
  val size = 16
}
