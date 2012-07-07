package scalaquest.sprite

import com.codahale.jerkson._
import scalaquest.util._

case class Animation(
  length: Int,
  row: Int
)

object Animation {
  object Format extends SimpleJsonFormat[Animation]
}

@JsonSnakeCase
case class Sprite(
  id: String,
  width: Int,
  height: Int,
  animations: Map[String, Animation],
  offsetX: Int,
  offsetY: Int
) {
  /** Convenience accessor that loads/caches the image for this sprite. */
  lazy val image = ImageLibrary.get(this)

  lazy val numAnimations: Int = animations.size

  lazy val maxAnimationLength: Int = animations.values.map(_.length).max

  def animation(name: String): Animation = {
    animations.get(name).getOrElse {
      throw new Exception("Animation not found: %s/%s".format(id, name))
    }
  }
}

object Sprite {
  /** Image scale: 1 = small, 2 = medium, 3 = large */
  val Scale = 2

  object Format extends SimpleJsonFormat[Sprite]
}
