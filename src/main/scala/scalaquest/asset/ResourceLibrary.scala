package scalaquest.asset

import java.net.URL
import scala.collection.mutable
import scala.io.Source

trait ResourceLibrary[Key, Resource] {
  // Public interface:

  /** Get a resource form the cache or from disk. */
  final def load(key: Key): Resource = {
    cache.get(key) getOrElse loadAndCache(key)
  }

  // Extension points:

  /** Turn a user-friendly resource key into a JAR resource URL. */
  private[asset] def url(key: Key): URL

  /** Load the resource from its URL and parse it into a T. */
  private[asset] def loadUrl(url: URL): Resource

  // Implementation:

  private val cache = mutable.HashMap[Key, Resource]()

  /** Get a resource from disk and add it to the cache. */
  private[asset] def loadAndCache(key: Key): Resource = {
    val ans = loadUrl(url(key))
    cache.put(key, ans)
    ans
  }
}
