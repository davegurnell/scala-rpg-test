package scalaquest.world

import scala.collection.mutable
import scalaquest.math._

class World(gen: ChunkGenerator) extends Grid[Square] {
  /** Chunks mapped against chunk coord (worldPos / chunkSize) */
  val chunks = new mutable.HashMap[(Int, Int), Chunk]

  def get(x: Int, y: Int): Square = {
    chunk(x / Chunk.size, y / Chunk.size).get(x % Chunk.size, y % Chunk.size)
  }

  def chunk(x: Int, y: Int): Chunk = {
    val pos = (x, y)
    chunks.get(pos) match {
      case Some(value) =>
        value
      case None =>
        println("Generating chunk (%s, %s): %s chunks loaded".format(x, y, chunks.size + 1))
        val chunk = gen(Vec(x * Chunk.size, y * Chunk.size))
        chunks.put(pos, chunk)
        chunk
    }
  }
}
