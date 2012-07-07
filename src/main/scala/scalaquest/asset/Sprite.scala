package scalaquest.asset

import java.awt._
import scalaquest.math._

case class Sprite(
  val name: String,
  val image: Image,
  val width: Int,
  val height: Int,
  val frames: Array[AxisBox],
  val offsetX: Int,
  val offsetY: Int,
  val flipX: Boolean,
  val flipY: Boolean
) {
  lazy val numFrames = frames.length
  def frame(num: Int): AxisBox = {
    frames(num % numFrames)
  }
}

object Sprite {
  def load(spriteName: String, rowName: String, flipX: Boolean = false, flipY: Boolean = false): Sprite = {
    val spriteMeta = SpriteMeta.load(spriteName)
    val animationMeta = spriteMeta.animation(rowName)
    Sprite(
      spriteName + "." + rowName,
      spriteMeta.image,
      spriteMeta.width,
      spriteMeta.height,
      (for(col <- 0 until animationMeta.length) yield {
        AxisBox.xywh(
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