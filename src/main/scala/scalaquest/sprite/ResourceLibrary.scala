package scalaquest.sprite

import java.net.URL
import scala.collection.mutable

trait ResourceLibrary[Key, Resource] {
  // Public interface:

  /** Get a resource form the cache or from disk. */
  def get(key: Key): Resource = {
    cache.get(key) getOrElse loadAndCache(key)
  }

  // Extension points:

  /** Turn a user-friendly resource key into a JAR resource URL. */
  private[sprite] def url(key: Key): URL

  /** Load the resource from its URL and parse it into a T. */
  private[sprite] def load(url: URL): Resource

  // Implementation:

  private val cache = mutable.HashMap[Key, Resource]()

  /** Get a resource from disk and add it to the cache. */
  private[sprite] def loadAndCache(key: Key): Resource = {
    val ans = load(url(key))
    cache.put(key, ans)
    ans
  }
}