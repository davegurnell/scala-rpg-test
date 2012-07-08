package scalaquest.swing

import java.awt._
import java.awt.image.BufferedImage
import java.awt.geom._
import scalaquest.asset._
import scalaquest.math._
import scalaquest.util._
import scalaquest.world._

class WorldRenderer(
  val world: World,
  val g: Graphics2D,
  val cameraPos: Vec,
  val zoom: Double = .5
) {
  val scale = TerrainTile.size * zoom
  val worldToViewTx = new AffineTransform(scale, 0, 0, scale, -scale * cameraPos.x, -scale * cameraPos.y)
  val viewToWorldTx = worldToViewTx.createInverse

  def viewToWorld(pos: Vec    ): Vec     = pos / scale + cameraPos
  def viewToWorld(box: AxisBox): AxisBox = box / scale + cameraPos
  def worldToView(pos: Vec    ): Vec     = (pos - cameraPos) * scale
  def worldToView(box: AxisBox): AxisBox = (box - cameraPos) * scale

  def renderSquares(viewBox: AxisBox): Unit = {
    val worldBox = viewToWorld(viewBox)
    g.transform(worldToViewTx)

    val yRange = worldBox.p0.y.floor.toInt to worldBox.p1.y.ceil.toInt
    val xRange = worldBox.p0.x.floor.toInt to worldBox.p1.x.ceil.toInt

    for(y <- yRange; x <- xRange) renderTerrain(x, y)
    for(y <- yRange; x <- xRange) renderFurniture(x, y)

    g.transform(viewToWorldTx)
  }

  def renderTerrain(x: Int, y: Int) = {
    g.drawImage(
      terrainTile(x, y),
      tileTransform(x, y),
      null
    )
  }

  def renderFurniture(x: Int, y: Int): Unit = {
    for {
      furnitureTile <- furnitureTile(x, y)
    } g.drawImage(
      furnitureTile,
      tileTransform(x, y),
      null
    )
  }

  def terrainTile(x: Int, y: Int): BufferedImage = {
    world.get(x, y).terrain match {
      case Water => TerrainTile.water
      case Earth => areaTile(x, y, Water, TerrainTile.earthOnWater)
      case Grass => areaTile(x, y, Earth, TerrainTile.grassOnEarth)
      case Scrub => areaTile(x, y, Grass, TerrainTile.scrubOnGrass)
      case Sand  => areaTile(x, y, Scrub, TerrainTile.sandOnScrub)
    }
  }

  def chooseN(x: Int, y: Int, tiles: Seq[BufferedImage]): BufferedImage = {
    tiles((x * 3 + y * 5) % tiles.length)
  }

  def choose2x2(x: Int, y: Int, tiles: Seq[BufferedImage]): BufferedImage = {
    tiles(x % 2 + 2 * y % 2)
  }

  def furnitureTile(x: Int, y: Int): Option[BufferedImage] = {
    world.get(x,  y).furniture match {
      case Some(Campfire) => None
      case None           => None
    }
  }

  def tileTransform(x: Int, y: Int): AffineTransform = {
    new AffineTransform(1.0 / TerrainTile.size, 0f, 0f, 1.0 / TerrainTile.size, x, y)
  }

  def areaTile(x: Int, y: Int, region: Region[BufferedImage]) = {
    region.Center.choose(
      x,
      y
    )
  }

  def areaTile(x: Int, y: Int, lower: Terrain, region: Region[BufferedImage]) = {
    region.choose(
      x,
      y,
      world.get(x, y-1).terrain == lower,
      world.get(x+1, y).terrain == lower,
      world.get(x, y+1).terrain == lower,
      world.get(x-1, y).terrain == lower
    )
  }
}
