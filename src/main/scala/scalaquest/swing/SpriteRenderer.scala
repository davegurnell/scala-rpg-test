package scalaquest.swing

import java.awt._
import scalaquest.math._
import scalaquest.sprite._

trait SpriteRenderer {

  // Metrics:

  def spriteSrcBox(sprite: Sprite, animation: Animation, index: Int): AxisBox = {
    AxisBox.xywh(
      Sprite.Scale * sprite.width  * index,
      Sprite.Scale * sprite.height * animation.row,
      Sprite.Scale * sprite.width,
      Sprite.Scale * sprite.height
    )
  }

  def spriteDesBox(sprite: Sprite, pos: Vec): AxisBox = {
    AxisBox.xywh(
      Sprite.Scale * pos.x,
      Sprite.Scale * pos.y,
      Sprite.Scale * sprite.width,
      Sprite.Scale * sprite.height
    )
  }

  // Painting:

  def fillSprite(g: Graphics2D, pos: Vec, sprite: Sprite, animation: Animation, index: Int): Unit = {
    val s = spriteSrcBox(sprite, animation, index)
    val d = spriteDesBox(sprite, pos)
    g.drawImage(
      sprite.image,
      d.x0.toInt,
      d.y0.toInt,
      d.x1.toInt,
      d.y1.toInt,
      s.x0.toInt,
      s.y0.toInt,
      s.x1.toInt,
      s.y1.toInt,
      null,
      null
    )
  }

}