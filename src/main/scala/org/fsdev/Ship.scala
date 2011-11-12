/**
 *
 */
package org.fsdev
import org.lwjgl.opengl.GL11._
import scala.math._
import scala.collection.mutable.LinkedList

/**
 * @author kjozsa
 */
class Ship(_x: Double, _y: Double, _angle: Double) extends Moveable(_x, _y, 1d, _angle) with Logger {
  override def bounding_box = (x - 5, x + 5, y, y + 15)

  var (r, g, b) = (random, random, random)

  // rotation
  var r_angle = 0d
  var r_dir = 1

  // speed
  var s_dir = 1
  var s_speed = 0d

  var shots = 0
  def shooting = shots > 0
  var ammos = new LinkedList[Ammo]()

  def tick() {
    if (alive) {
      alter_speed()
      turn()
      shoot()
      update_xy()
      draw()
    }
  }

  def alter_speed() {
    s_speed += 0.3 * random * s_dir
    s_speed = scala.math.min(5, s_speed)
    s_speed = scala.math.max(3, s_speed)

    //    println("speed: " + speed + ", s_speed: " + s_speed + ", s_dir: " + s_dir)

    if (speed < 1.4) s_dir = 1
    else if (speed > 10) s_dir = -1
    else chance(0.2) { s_dir *= -1 }

    speed += s_speed
    speed = scala.math.max(s_speed, 0.2)
  }

  def turn() {
    r_angle += 0.3 * random * r_dir
    if (abs(r_angle) < 6) heading += r_angle
    chance(0.5) { r_dir *= -1 }
  }

  def shoot() {
    if (shooting) {
      ammos ++= List(new Ammo(x, y, speed * 2, heading, ammos, (r, g, b)))
      shots -= 1
    } else chance(0.015) {
      shots = 3 + (random * 10).toInt
    }
  }

  def draw() {
    //    draw_bounding_box()
    ammos.map(_.tick)

    glPushMatrix()
    glTranslated(x, y, 0)
    glRotated(heading, 0, 0, 1)
    glColor3d(r, g, b)

    glBegin(GL_TRIANGLE_FAN)
    glVertex2i(0, 0)
    glVertex2i(6, 15)
    glVertex2i(-6, 15)
    glEnd()
    glPopMatrix()
  }

  def draw_bounding_box() {
    glPushMatrix()
    glTranslated(x, y, 0)
    glRotated(heading, 0, 0, 1)
    glColor3d(r, g, b)

    glBegin(GL_QUADS)
    glVertex2d(-5, 5)
    glVertex2d(-5, 15)
    glVertex2d(5, 15)
    glVertex2d(5, 0)
    glEnd()
    glPopMatrix()
  }
}