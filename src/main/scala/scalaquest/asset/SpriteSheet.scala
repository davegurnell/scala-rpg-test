package scalaquest.asset

import com.codahale.jerkson._
import java.net.URL
import scala.io.Source
import scalaquest.util._

case class AnimationMeta(
  val length: Int,
  val row: Int
)

@JsonSnakeCase
case class SpriteMeta(
  val id: String,
  val width: Int,
  val height: Int,
  val animations: Map[String, AnimationMeta],
  val offsetX: Int,
  val offsetY: Int
) {
  /** Convenience accessor that loads/caches the image for this sprite. */
  lazy val image = ImageLibrary.load(id)

  lazy val rows: Int = animations.size
  lazy val cols: Int = animations.values.map(_.length).max

  def animation(name: String): AnimationMeta = {
    animations.get(name).getOrElse {
      throw new Exception("AnimationMeta not found: %s/%s".format(id, name))
    }
  }
}

object SpriteMeta extends ResourceLibrary[String, SpriteMeta] {
  object SheetFormat extends SimpleJsonFormat[SpriteMeta]

  /** Turn a sprite name ("agent") into a JAR URL ("file://.../sprites/agent.json"). */
  private[asset] def url(name: String): URL = {
    getClass.getResource("/sprites/%s.json".format(name))
  }

  /** Load a resource from its URL */
  private[asset] def loadUrl(url: URL): SpriteMeta = {
    SheetFormat.unapply(Source.fromURL(url).mkString).get
  }
}
