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
      desert(pos)
    } else if(moisture < .4) {
      scrub(pos)
    } else if(moisture < .6) {
      grassland(pos)
    } else if(moisture < .8) {
      earth(pos)
    } else {
      water(pos)
    }
  }

  def desert(pos: Vec) = {
    val level = noise(pos / 10)
    if(level < .5) {
      Square(SandFlat, possible(Tumbleweed, pos))
    } else if(level < .8) {
      Square(SandSemi, possible(Tumbleweed, pos))
    } else {
      Square(SandDune, possible(Tumbleweed, pos))
    }
  }

  def scrub(pos: Vec) = {
    Square(Scrub, possible(Leaf, pos))
  }

  def grassland(pos: Vec) = {
    Square(Grass, possible(Leaf, pos))
  }

  def earth(pos: Vec) = {
    Square(Earth, possible(Shell, pos))
  }

  def water(pos: Vec) = {
    Square(Water, None)
  }

  def possible(item: Furniture, pos: Vec): Option[Furniture] = {
    val level = noise(pos / 5)
    if(level < .1) {
      Some(item)
    } else if(level > .9) {
      Some(Campfire)
    } else {
      None
    }
  }
}
