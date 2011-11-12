/**
 *
 */
package org.fsdev

import org.lwjgl.opengl.GL11._
import scala.collection.mutable.LinkedList

/**
 * @author kjozsa
 */
class Ammo(_x: Double, _y: Double, _speed: Double, _heading: Double, list: LinkedList[Ammo], rgb: Tuple3[Double, Double, Double])
  extends Moveable(_x, _y, _speed, _heading) {

  override def bounding_box = (x - 1, x + 1, y - 1, y + 1)
  val (r, g, b) = rgb
  var life = 100

  def tick() {
    if (alive) {
      check_life()
      update_xy()

      draw()
    }
  }

  def check_life() {
    life -= 1

    if (life == 0) {
      alive = false
      list.drop(0)
    }
  }

  def draw() {
    glPushMatrix()
    glTranslated(x, y, 0)
    glColor3d(r, g, b)

    glBegin(GL_QUADS)
    glVertex2d(-1, -1)
    glVertex2d(-1, 1)
    glVertex2d(1, 1)
    glVertex2d(1, -1)
    glEnd()
    glPopMatrix()
  }
}