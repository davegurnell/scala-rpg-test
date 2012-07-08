package scalaquest.world

import scala.collection.mutable
import scalaquest.asset._
import scalaquest.math._

trait ChunkGenerator {
  def apply(chunkX: Int, chunkY: Int): Chunk = {
    apply(Vec(chunkX * 16, chunkY * 16))
  }

  def apply(chunkPos: Vec): Chunk

  def simplex(pos: Vec): Double = {
    SimplexNoise.noise(pos.x, pos.y)
  }
}

object TestGenerator extends ChunkGenerator {
  def apply(chunkPos: Vec): Chunk = {
    val tiles =
      for {
        y <- 0 until Chunk.size
        x <- 0 until Chunk.size
      } yield {
        val pos = chunkPos + Vec(x, y)
        if(simplex(pos / 10) < .6) {
          Tile.grass
        } else {
          Tile.pavement
        }
      }

    Chunk(mutable.ArraySeq(tiles : _*))
  }
}
