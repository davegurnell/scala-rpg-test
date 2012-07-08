package scalaquest.world

sealed trait Terrain

case object Water extends Terrain
case object Earth extends Terrain
case object Grass extends Terrain
case object Scrub extends Terrain
case object Sand  extends Terrain
