package scalaquest.world

sealed trait Terrain { val level: Int }

case object Water extends Terrain { val level = 0 }
case object Earth extends Terrain { val level = 1 }
case object Grass extends Terrain { val level = 2 }
case object Scrub extends Terrain { val level = 3 }

trait Sand  extends Terrain
case object SandFlat extends Sand { val level = 4 }
case object SandSemi extends Sand { val level = 4 }
case object SandDune extends Sand { val level = 4 }