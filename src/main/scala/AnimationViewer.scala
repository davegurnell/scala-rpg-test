package scalaquest

import java.awt._
import javax.swing._
import scalaquest.math._
import scalaquest.sprite._
import scalaquest.swing._

object AnimationViewer {
  /** Main entry point */
  def main(args: Array[String]): Unit = {
    args.toList match {
      case spriteName :: _ =>
        showSprite(spriteName)

      case _ =>
        println("Usage: SpriteViewer <spriteName>")
        exit(1)
    }
  }

  /** Show a Sprite viewer with the following sprite */
  def showSprite(name: String): Unit = {
    val sprite = SpriteLibrary.get(name)

    println("Viewing " + sprite)

    val frame = new JFrame("SpriteViewer: " + name)
    val canvas = new SpritePanel(sprite)
    frame.setContentPane(canvas)
    frame.pack()
    frame.setVisible(true)

    while(true) {
      Thread.sleep(50)
      canvas.repaint()
    }
  }

  class SpritePanel(val sprite: Sprite) extends JPanel with SpriteRenderer {
    setPreferredSize(new Dimension(
      Sprite.Scale * 50 * sprite.maxAnimationLength,
      Sprite.Scale * 50 * sprite.numAnimations
    ))

    override def paintComponent(graphics: Graphics): Unit = {
      val time = (System.currentTimeMillis / 200)

      val g = graphics.asInstanceOf[Graphics2D]
      for {
        animation <- sprite.animations.values
      } yield {
        val frame = (time % animation.length).toInt

        val pos = Vec(0, 50 * animation.row)
        val des = spriteDesBox(sprite, pos)
        g.setColor(Color.WHITE)
        g.fillRect(des.x0.toInt, des.y0.toInt, des.w.toInt, des.h.toInt)
        fillSprite(g, pos, sprite, animation, frame)
        g.setColor(Color.BLACK)
        g.drawRect(des.x0.toInt, des.y0.toInt, des.w.toInt, des.h.toInt)
      }
    }
  }
}