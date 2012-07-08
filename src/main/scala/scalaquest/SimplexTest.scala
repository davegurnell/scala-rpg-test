package scalaquest

import java.awt._
import java.awt.event._
import javax.swing._
import scalaquest.math._
import scalaquest.asset._
import scalaquest.swing._

object SimplexTest {
  def main(args: Array[String]): Unit = {
    showFrame()
  }

  def showFrame(): Unit = {
    val frame = new JFrame("Simplex test")
    val canvas = new SimplexCanvas
    frame.setContentPane(canvas)
    frame.pack()
    frame.setVisible(true)

    while(true) {
      Thread.sleep(30)
      canvas.frame()
    }
  }

  class SimplexCanvas extends JPanel {
    val tileSize = 16
    var viewPos = Vec(0, 0)

    setPreferredSize(new Dimension(
      500,
      500
    ))

    def frame(): Unit = {
      viewPos += Vec(.1, .1)
      repaint()
    }

    def gray(level: Double): Color = {
      val l = level.toFloat
      new Color(l, l, l)
    }

    def noise(pos: Vec, freq: Double, amp: Double): Double = {
      SimplexNoise.noise(pos.x * freq, pos.y * freq) * amp
    }

    def threshold(value: Double, threshold: Double = .5): Double = {
      if(value > threshold) 1.0 else 0.0
    }

    override def paintComponent(graphics: Graphics): Unit = {
      val g = graphics.asInstanceOf[Graphics2D]
      val size = getSize()
      for {
        x <- 0.until(size.width, tileSize)
        y <- 0.until(size.height, tileSize)
        val screenPos = Vec(x, y)
        val worldPos  = viewPos + screenPos/tileSize
        // val level = .5 + noise(worldPos, .01, .3)
        val level = .5 + noise(worldPos, .05, .3) + noise(worldPos, .2, .2)
        // val level = threshold(.5 + noise(worldPos, .01, .3) + noise(worldPos, .1, .2))
      } {
        g.setColor(gray(level))
        g.fillRect(
          screenPos.x.toInt,
          screenPos.y.toInt,
          tileSize,
          tileSize
        )
      }
    }
  }
}
