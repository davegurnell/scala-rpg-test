package scalaquest.asset

import java.awt.Image
import java.net.URL
import javax.swing.ImageIcon

/**
 * Image loader and cache. Invoke using the get() method from ResourceLibrary, e.g.:
 *
 *     ImageLibrary.load(SpriteLoader.get("agent").id)
 */
object ImageLibrary extends ResourceLibrary[String, Image] {
  /** Turn a sprite name ("agent") into a JAR URL ("file://.../img/1/agent.png"). */
  private[asset] def url(name: String): URL = {
    Option(getClass.getResource("/img/1/%s.png".format(name))).getOrElse {
      throw new Exception("Image asset not found: " + name)
    }
  }

  /** Load a resource from its URL */
  private[asset] def loadUrl(url: URL): Image = {
    new ImageIcon(url).getImage
  }
}