package scalaquest

import java.awt._
import javax.swing._
import scalaquest.math._
import scalaquest.asset._
import scalaquest.swing._

object AnimationTest {
  def main(args: Array[String]): Unit = {
    args.toList match {
      case spriteName :: _ => showFrame(spriteName)
      case _ => showFrame("leatherarmor")
    }
  }

  /** Show a Sprite viewer with the following sprite */
  def showFrame(spriteName: String): Unit = {
    val frame = new JFrame("Animation test: " + spriteName)
    val canvas = new SpritePanel(Sprite.loadAll(spriteName))
    frame.setContentPane(canvas)
    frame.pack()
    frame.setVisible(true)

    while(true) {
      Thread.sleep(50)
      canvas.repaint()
    }
  }

  class SpritePanel(val sprites: Seq[Sprite]) extends JPanel with SpriteRenderer {
    setPreferredSize(new Dimension(
      TerrainTile.size * 20,
      TerrainTile.size * sprites.length * 4
    ))

    override def paintComponent(graphics: Graphics): Unit = {
      val time = (System.currentTimeMillis / 200)

      val g = graphics.asInstanceOf[Graphics2D]
      for {
        (sprite, row) <- sprites.zipWithIndex
      } {
        val rowPos = Vec(0, row) * 4

        for {
          x <- 0 to 20
          y <- 0 to 2
          val tile = if(x == 1 && y == 1) TerrainTile.grassOnEarth.Center.TL else TerrainTile.earthOnWater.Center.TL
          val pos  = rowPos + Vec(x, y)
        } paintTile(g, pos * TerrainTile.size, tile)

        paintSprite(g, rowPos + Vec(1, 1), sprite)
        paintLabel(g, rowPos + Vec(2.75, 1.75), sprite.name)
      }
    }
  }
}