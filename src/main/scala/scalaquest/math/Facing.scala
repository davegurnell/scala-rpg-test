package scalaquest.math

sealed trait Facing { val unit: Vec }
case object North extends Facing { val unit = Vec( 0, -1) }
case object East  extends Facing { val unit = Vec( 1,  0) }
case object South extends Facing { val unit = Vec( 0,  1) }
case object West  extends Facing { val unit = Vec(-1,  0) }
