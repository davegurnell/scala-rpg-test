package scalaquest

import java.awt._
import java.awt.event._
import javax.swing._
import scalaquest.math._
import scalaquest.asset._
import scalaquest.swing._
import scalaquest.world._

object WorldTest {
  def main(args: Array[String]): Unit = {
    showFrame()
  }

  def showFrame(): Unit = {
    val frame = new JFrame("World test")
    val canvas = new WorldCanvas(new World(TestGenerator))
    frame.setContentPane(canvas)
    frame.pack()
    frame.setVisible(true)

    while(true) {
      Thread.sleep(30)
      canvas.frame()
    }
  }

  class WorldCanvas(val world: World) extends JPanel with SpriteRenderer {
    var viewPos = Vec(0, 0)

    setPreferredSize(new Dimension(
      500,
      500
    ))

    def frame(): Unit = {
      viewPos += Vec(.1, .1)
      repaint()
    }

    override def paintComponent(graphics: Graphics): Unit = {
      val g = graphics.asInstanceOf[Graphics2D]
      val size = getSize()
      val viewBox = AxisBox.xywh(viewPos.x, viewPos.y, size.width / Tile.size, size.height / Tile.size)

      for {
        y <- viewBox.y0.toInt until viewBox.y1.ceil.toInt
        x <- viewBox.x0.toInt until viewBox.x1.ceil.toInt
        val worldPos = Vec(x, y)
        val screenPos = (worldPos - viewPos) * Tile.size
      } paintTile(g, screenPos, world.get(worldPos))
    }
  }
}
