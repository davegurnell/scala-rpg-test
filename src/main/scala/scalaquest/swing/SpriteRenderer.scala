package scalaquest.swing

import java.awt._
import java.awt.image.BufferedImage
import scalaquest.math._
import scalaquest.asset._

trait SpriteRenderer {
  // Frame rate:

  val frameRate = 5 // fps
  val startTime = System.currentTimeMillis

  def currentFrame: Int = {
    ((System.currentTimeMillis - startTime) * frameRate / 1000L).toInt
  }

  // Metrics:

  def worldToScreen(pos: Vec) = pos * TerrainTile.size

  def tileSrcBox(tile: BufferedImage): AxisBox = {
    AxisBox.xywh(
      0,
      0,
      TerrainTile.size,
      TerrainTile.size
    )
  }

  def tileDesBox(pos: Vec): AxisBox = {
    AxisBox.xywh(
      pos.x,
      pos.y,
      TerrainTile.size,
      TerrainTile.size
    )
  }

  def spriteDesBox(sprite: Sprite, pos: Vec): AxisBox = {
    val screenPos = worldToScreen(pos)
    val x0 = screenPos.x + sprite.offsetX
    val y0 = screenPos.y + sprite.offsetY
    val x1 = x0 + sprite.width
    val y1 = y0 + sprite.height
    if(sprite.flipX) {
      if(sprite.flipY) {
        AxisBox(x1, y1, x0, y0)
      } else {
        AxisBox(x1, y0, x0, y1)
      }
    } else {
      if(sprite.flipY) {
        AxisBox(x0, y1, x1, y0)
      } else {
        AxisBox(x0, y0, x1, y1)
      }
    }
  }

  // Painting:

  def paintTile(g: Graphics2D, pos: Vec, tile: BufferedImage): Unit = {
    paintImage(
      g,
      tile,
      tileSrcBox(tile),
      tileDesBox(pos)
    )
  }

  def paintSprite(g: Graphics2D, pos: Vec, sprite: Sprite, frameNumber: Int = currentFrame): Unit = {
    paintImage(
      g,
      sprite.frame(frameNumber),
      AxisBox(0, 0, sprite.width, sprite.height),
      spriteDesBox(sprite, pos)
    )
  }

  def paintTileBox(g: Graphics2D, pos: Vec, paint: Paint = Color.BLACK): Unit = {
    val box = tileDesBox(pos)
    g.setPaint(paint)
    g.drawRect(
      box.x0.toInt,
      box.y0.toInt,
      box.w.toInt,
      box.w.toInt
    )
  }

  def paintLabel(g: Graphics2D, pos: Vec, label: String, paint: Paint = Color.BLACK): Unit = {
    val screenPos = worldToScreen(pos)
    g.setPaint(paint)
    g.drawString(
      label,
      screenPos.x.toInt,
      screenPos.y.toInt
    )
  }

  def paintImage(g: Graphics2D, image: Image, src: AxisBox, des: AxisBox): Unit = {
    g.drawImage(
      image,
      des.a.x.toInt,
      des.a.y.toInt,
      des.b.x.toInt,
      des.b.y.toInt,
      src.a.x.toInt,
      src.a.y.toInt,
      src.b.x.toInt,
      src.b.y.toInt,
      null,
      null
    )
  }
}