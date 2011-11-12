/**
 *
 */
package org.fsdev

import scala.math._

/**
 * @author kjozsa
 */
abstract class Moveable(_x: Double, _y: Double, _speed: Double, _heading: Double) {
  var (x, y) = (_x, _y)
  var speed = _speed
  var heading = _heading
  var alive = true

  override def toString() = {
    val (x1, x2, y1, y2) = bounding_box
    "x1: " + x1.toInt + ", x2: " + x2.toInt + ", y1: " + y1.toInt + ", y2: " + y2.toInt + ", heading: " + heading.toInt
  }

  def update_xy() {
    if (x < 5 || x > Game.screen_x - 5) heading = 360 - heading
    if (y < 5 || y > Game.screen_y - 5) heading = 180 - heading

    if (heading < 0) heading += 360
    if (heading > 360) heading -= 360

    var rad = (heading - 90) * Pi / 180
    x += speed * cos(rad)
    y += speed * sin(rad)
  }

  def bounding_box: Tuple4[Double, Double, Double, Double]

  def collided(other: Moveable) = {
    val (x1, x2, y1, y2) = bounding_box
    val (other_x1, other_x2, other_y1, other_y2) = other.bounding_box

    alive && other.alive &&
      x1 > other_x1 && x1 < other_x2 &&
      y2 > other_y1 && y2 < other_y2
  }
}