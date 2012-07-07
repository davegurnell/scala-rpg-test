package scalaquest.sprite

import java.awt.Image
import java.net.URL
import javax.swing.ImageIcon

/**
 * Image loader and cache. Invoke using the get() method from ResourceLibrary, e.g.:
 *
 *     ImageLibrary.get(SpriteLoader.get("agent"))
 */
object ImageLibrary extends ResourceLibrary[Sprite, Image] {
  /** Turn a sprite and scale into a JAR URL ("file://.../img/2/agent.png"). */
  private[sprite] def url(sprite: Sprite): URL = {
    getClass.getResource("/img/%s/%s.png".format(Sprite.Scale, sprite.id))
  }

  /** Load a resource from its URL */
  private[sprite] def load(url: URL): Image = {
    new ImageIcon(url).getImage
  }
}