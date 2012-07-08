package scalaquest.util

trait Corners[T] {
  def TL: T
  def TR: T
  def BL: T
  def BR: T

  def choose(x: Int, y: Int): T = {
    if(x % 2 == 0) {
      if( y % 2 == 0) {
        TL
      } else {
        BL
      }
    } else {
      if( y % 2 == 0) {
        TR
      } else {
        BR
      }
    }
  }
}

trait Region[T] {
  def Center: Corners[T]
  def Top: T
  def Right: T
  def Bottom: T
  def Left: T
  def Inner: Corners[T]
  def Outer: Corners[T]

  def choose(x: Int, y: Int, t: Boolean, r: Boolean, b: Boolean, l: Boolean): T = {
    if(t) {
      if(l) {
        Outer.TL
      } else if(r) {
        Outer.TR
      } else {
        Top
      }
    } else if(b) {
      if(l) {
        Outer.BL
      } else if(r) {
        Outer.BR
      } else {
        Bottom
      }
    } else {
      if(l) {
        Left
      } else if(r) {
        Right
      } else Center.choose(x, y)
    }
  }
}
