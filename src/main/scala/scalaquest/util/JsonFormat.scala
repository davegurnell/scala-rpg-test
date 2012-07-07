package scalaquest.util

import com.codahale.jerkson.Json

trait JsonReader[T] {
  def unapply(in: String)(implicit m: Manifest[T]): Option[T]
}

trait JsonWriter[T] {
  def apply(in: T): String
}

trait JsonFormat[T] extends JsonReader[T] with JsonWriter[T]

trait SimpleJsonFormat[T] extends JsonFormat[T] {
  def unapply(in: String)(implicit m: Manifest[T]): Option[T] = {
    try {
      Some(Json.parse[T](in))
    } catch {
      case exn =>
        exn.printStackTrace
        None
    }
  }

  def apply(in: T): String = {
    Json.generate(in)
  }
}