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

  class WorldCanvas(val world: World) extends JPanel {
    var viewPos = Vec(0, 0)

    setPreferredSize(new Dimension(500, 500))

    def frame(): Unit = {
      viewPos += Vec(.1, .1)
      repaint()
    }

    override def paintComponent(graphics: Graphics): Unit = {
      val renderer = new WorldRenderer(world, graphics.asInstanceOf[Graphics2D], viewPos)
      val size = getSize()
      renderer.renderSquares(AxisBox(0, 0, size.width, size.height))
    }
  }
}
