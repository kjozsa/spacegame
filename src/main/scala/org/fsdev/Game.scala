/**
 *
 */
package org.fsdev
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.DisplayMode
import scala.math.{ random => random }

/**
 * @author kjozsa
 */
class Game {
  val ships = for {
    i <- 1 to 20
    ship = new Ship(random * (Game.screen_x - 20) + 10, random * (Game.screen_y - 20) + 10, random * 360)
  } yield ship

  var alive_ships = ships

  def tick() {
    glClear(GL_COLOR_BUFFER_BIT)
    ships.foreach(_.tick)
    alive_ships = alive_ships.filter(_.alive)

    alive_ships.foreach { ship1 =>
      alive_ships.foreach { ship2 =>
        if (ship1.collided(ship2)) {
          Array(ship1, ship2).foreach(_.alive = false)
        }

        ship1.ammos.foreach { ammo =>
          if (ammo.collided(ship2) && ship1 != ship2) {
            ship2.alive = false
            ammo.alive = false
          }
        }
      }
    }
  }
}

object Game extends App with Logger {
  if (System.getProperty("org.lwjgl.librarypath") == null) System.setProperty("org.lwjgl.librarypath", "/usr/share/lwjgl/native/linux/")
  info("using lwjgl library from " + System.getProperty("org.lwjgl.librarypath"))

  val (screen_x, screen_y) = (800, 600)
  val fps = new FPS
  var game = new Game

  def first_time_init() {
    Display.setDisplayMode(new DisplayMode(Game.screen_x, Game.screen_y))
    Display.create()

    glMatrixMode(GL_PROJECTION)
    glLoadIdentity()
    glOrtho(0, Game.screen_x, Game.screen_y, 0, 0, 1)
    glMatrixMode(GL_MODELVIEW)
    glDisable(GL_DEPTH_TEST) // go 2D
    glEnable(GL_POINT_SMOOTH | GL_LINE_SMOOTH)
    glHint(GL_POINT_SMOOTH_HINT | GL_LINE_SMOOTH_HINT, GL_NICEST)
  }

  first_time_init()

  while (!Display.isCloseRequested()) {
    game.tick
    fps.tick

    Display.update()
    Display.sync(60)

    if (game.alive_ships.length < 2) {
      Thread.sleep(1000)
      game = new Game
    }
  }

  Display.destroy()
}
