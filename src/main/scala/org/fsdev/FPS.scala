/**
 *
 */
package org.fsdev
import org.lwjgl.Sys
import org.lwjgl.opengl.Display

/**
 * @author kjozsa
 */
class FPS {
  private var lastMeasured = currentTime
  private var fps = 0

  def currentTime = (Sys.getTime() * 1000) / Sys.getTimerResolution()

  def tick {
    if (currentTime - lastMeasured > 1000) {
      Display.setTitle("FPS: " + fps)
      fps = 0
      lastMeasured += 1000;
    }

    fps += 1
  }
}
