package scalaquest.world

import scala.collection.mutable
import scalaquest.asset._
import scalaquest.math._

trait ChunkGenerator {
  def apply(chunkPos: Vec): Chunk
}

object TestGenerator extends ChunkGenerator {
  def apply(chunkPos: Vec): Chunk = {
    Chunk(mutable.ArraySeq(generate(chunkPos: Vec) : _*))
  }

  def noise(pos: Vec): Double = {
    .5 + SimplexNoise.noise(pos.x, pos.y) / 2
  }

  def generate(chunkPos: Vec): Seq[Square] = {
    for {
      y <- 0 until Chunk.size
      x <- 0 until Chunk.size
      val pos = chunkPos + Vec(x, y)
    } yield square(pos)
  }

  def square(pos: Vec): Square = {
    val moisture = noise(pos / 50) * .8 + noise(pos / 20) * .2
    if(moisture < .2) {
      Square(Sand, None)
    } else if(moisture < .4) {
      Square(Scrub, None)
    } else if(moisture < .6) {
      Square(Grass, None)
    } else if(moisture < .8) {
      Square(Earth, None)
    } else {
      Square(Water, None)
    }
  }

  // def water(pos: Vec) = {
  //   Water
  // }

  // def grassland(pos: Vec) = {
  //   val level = noise(pos)
  //   if(level < .7) {
  //     Grass
  //   } else {
  //     Flowers
  //   }
  // }

  // def scrub(pos: Vec) = {
  //   Scrub
  // }

  // def desert(pos: Vec) = {
  //   val level = noise(pos / 10)
  //   if(level < .5) {
  //     SandFlat
  //   } else if(level < .8) {
  //     SandSemi
  //   } else {
  //     SandDune
  //   }
  // }

  // def furniture(pos: Vec): Option[Furniture] = {
  //   val level = noise(pos / 5)
  //   if(level < .9) {
  //     None
  //   } else {
  //     Some(Campfire)
  //   }
  // }
}
