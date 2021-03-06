package scalaquest

import java.awt._
import java.awt.event._
import javax.swing._
import scalaquest.math._
import scalaquest.asset._
import scalaquest.swing._

object MovementTest {
  def main(args: Array[String]): Unit = {
    args.toList match {
      case spriteName :: _ => showFrame(spriteName)
      case _ => showFrame("leatherarmor")
    }
  }

  def showFrame(spriteName: String): Unit = {
    val frame = new JFrame("Movement test: " + spriteName)
    val canvas = new MovementCanvas(spriteName)
    frame.setContentPane(canvas)
    frame.addKeyListener(canvas)
    frame.pack()
    frame.setVisible(true)

    while(true) {
      Thread.sleep(50)
      canvas.frame()
    }
  }

  class MovementCanvas(val spriteName: String) extends JPanel with SpriteRenderer with KeyListener {

    val worldSize = 16

    var playerPos = Vec(worldSize, worldSize) / 21
    var playerFacing: Facing = South
    var playerSpeed: Double = 0.0
    val playerMaxSpeed: Double = 0.1
    val playerSpritesIdle = Map[Facing, Sprite](
      North -> Sprite.load(spriteName, "idle_up"),
      East  -> Sprite.load(spriteName, "idle_right"),
      South -> Sprite.load(spriteName, "idle_down"),
      West  -> Sprite.load(spriteName, "idle_right", flipX = true)
    )
    val playerSpritesWalk = Map[Facing, Sprite](
      North -> Sprite.load(spriteName, "walk_up"),
      East  -> Sprite.load(spriteName, "walk_right"),
      South -> Sprite.load(spriteName, "walk_down"),
      West  -> Sprite.load(spriteName, "walk_right", flipX = true)
    )

    var initialized = false

    setPreferredSize(new Dimension(
      TerrainTile.size * worldSize,
      TerrainTile.size * worldSize
    ))

    def frame(): Unit = {
      playerPos += playerFacing.unit * playerSpeed
      repaint()
    }

    override def paintComponent(graphics: Graphics): Unit = {
      val g = graphics.asInstanceOf[Graphics2D]
      if(initialized) {
        paintWorldUnderPlayer(g)
      } else {
        paintWorld(g)
        initialized = true
      }
      paintPlayer(g)
    }

    def paintWorld(g: Graphics2D): Unit = {
      for {
        j <- 0 until worldSize
        i <- 0 until worldSize
      } paintTile(g, Vec(i, j) * TerrainTile.size, TerrainTile.grassOnEarth.Center.TL)
    }

    def paintWorldUnderPlayer(g: Graphics2D): Unit = {
      val base = playerPos.floor
      for(x <- -1 to 1; y <- -1 to 1) {
        paintTile(g, (base + Vec(x, y)) * TerrainTile.size, TerrainTile.grassOnEarth.Center.TL)
      }
    }

    def paintPlayer(g: Graphics2D): Unit = {
      paintSprite(
        g,
        playerPos,
        if(playerSpeed > 0) {
          playerSpritesWalk.get(playerFacing).get
        } else {
          playerSpritesIdle.get(playerFacing).get
        }
      )
    }

    def keyTyped(evt: KeyEvent): Unit = {}

    def keyPressed(evt: KeyEvent): Unit = {
      evt.getKeyCode match {
        case KeyEvent.VK_UP =>
          playerFacing = North
          playerSpeed  = playerMaxSpeed

        case KeyEvent.VK_RIGHT =>
          playerFacing = East
          playerSpeed  = playerMaxSpeed

        case KeyEvent.VK_DOWN =>
          playerFacing = South
          playerSpeed  = playerMaxSpeed

        case KeyEvent.VK_LEFT =>
          playerFacing = West
          playerSpeed  = playerMaxSpeed

        case _ =>
          // Do nothing
      }
    }

    def keyReleased(evt: KeyEvent): Unit = {
      playerSpeed = 0
    }
  }
}
