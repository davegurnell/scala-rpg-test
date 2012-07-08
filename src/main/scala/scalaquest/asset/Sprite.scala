package scalaquest.asset

import java.awt._
import java.awt.image.BufferedImage
import scalaquest.math._

case class Sprite(
  val name: String,
  val width: Int,
  val height: Int,
  val frames: Array[BufferedImage],
  val offsetX: Int,
  val offsetY: Int,
  val flipX: Boolean,
  val flipY: Boolean
) {
  lazy val numFrames = frames.length
  def frame(num: Int): BufferedImage = {
    frames(num % numFrames)
  }
}

object Sprite {
  def load(spriteName: String, rowName: String, flipX: Boolean = false, flipY: Boolean = false): Sprite = {
    val spriteMeta = SpriteMeta.load(spriteName)
    val animationMeta = spriteMeta.animation(rowName)
    Sprite(
      spriteName + "." + rowName,
      spriteMeta.width,
      spriteMeta.height,
      (for(col <- 0 until animationMeta.length) yield {
        spriteMeta.image.getSubimage(
          col * spriteMeta.width,
          animationMeta.row * spriteMeta.height,
          spriteMeta.width,
          spriteMeta.height
        )
      }).toArray,
      spriteMeta.offsetX,
      spriteMeta.offsetY,
      flipX,
      flipY
    )
  }

  def loadAll(spriteName: String): Seq[Sprite] = {
    val spriteMeta = SpriteMeta.load(spriteName)
    for {
      (rowName, row) <- spriteMeta.animations.toSeq
    } yield load(spriteName, rowName)
  }
}