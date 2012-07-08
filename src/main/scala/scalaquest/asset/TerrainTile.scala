package scalaquest.asset

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import scalaquest.util._

object TerrainTile {
  val size = 16 // pixels
  val image = ImageLibrary.load("tilesheet")

  // Tiles:

  val water           = single( 5, 20)
  val earthOnWater    = region(11, 27,  8, 24,  5, 20)
  val grassOnEarth    = region(14,  7, 11,  4, 10, 27)
  val scrubOnGrass    = region( 5, 27,  2, 24, 12,  7)
  val sandFlatOnScrub = region(18, 14, 14, 24,  4, 27)
  val sandSemiOnScrub = region(18, 16, 14, 24,  4, 27)
  val sandDuneOnScrub = region(18, 18, 14, 24,  4, 27)

  val campfire   = single( 5,  0)
  val tumbleweed = single(11, 15)
  val leaf       = single(17,  5)
  val shell      = single( 5, 91)

  // Composition and arrangement:

  def single(x: Int, y: Int): BufferedImage = {
    image.getSubimage(x * size, y * size, size, size)
  }

  def compose(a: BufferedImage, b: BufferedImage): BufferedImage = {
    val ans = new BufferedImage(a.getWidth, a.getHeight, a.getType)
    val g = ans.getGraphics.asInstanceOf[Graphics2D]
    g.drawImage(a, 0, 0, null)
    g.drawImage(b, 0, 0, null)
    ans
  }

  def corners(x: Int, y: Int): Corners[BufferedImage] = {
    new Corners[BufferedImage] {
      val TL = single(x,   y  )
      val TR = single(x+1, y  )
      val BL = single(x,   y+1)
      val BR = single(x+1, y+1)
    }
  }

  def region(cx: Int, cy: Int, fgx: Int, fgy: Int, bgx: Int, bgy: Int): Region[BufferedImage] = {
    val bg = single(bgx, bgy)
    new Region[BufferedImage] {
      val Top    = compose(bg, single(fgx+1, fgy+0))
      val Right  = compose(bg, single(fgx+5, fgy+4))
      val Bottom = compose(bg, single(fgx+1, fgy+5))
      val Left   = compose(bg, single(fgx+0, fgy+4))
      val Center = new Corners[BufferedImage] {
        val TL   = single(cx+0, cy+0)
        val TR   = single(cx+1, cy+0)
        val BR   = single(cx+1, cy+1)
        val BL   = single(cx+0, cy+1)
      }
      val Outer  = new Corners[BufferedImage] {
        val TL   = compose(bg, single(fgx+0, fgy+0))
        val TR   = compose(bg, single(fgx+5, fgy+0))
        val BR   = compose(bg, single(fgx+5, fgy+5))
        val BL   = compose(bg, single(fgx+0, fgy+5))
      }
      val Inner  = new Corners[BufferedImage] {
        val TL   = compose(bg, single(fgx+1, fgy+1))
        val TR   = compose(bg, single(fgx+4, fgy+1))
        val BR   = compose(bg, single(fgx+2, fgy+3))
        val BL   = compose(bg, single(fgx+2, fgy+2))
      }
    }
  }
}
