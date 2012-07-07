package scalaquest.sprite

import java.net.URL
import scala.io.Source

/**
 * Sprite loader and cache. Invoke using the get() method from ResourceLibrary, e.g.:
 *
 *     SpriteLibrary.get("agent")
 */
object SpriteLibrary extends ResourceLibrary[String, Sprite] {
  /** Turn a sprite name ("agent") into a JAR URL ("file://.../sprites/agent.json"). */
  private[sprite] def url(name: String): URL = {
    getClass.getResource("/sprites/%s.json".format(name))
  }

  /** Load a resource from its URL */
  private[sprite] def load(url: URL): Sprite = {
    Sprite.Format.unapply(Source.fromURL(url).mkString).get
  }
}