/**
 *
 */
package org

/**
 * @author kjozsa
 */
package object fsdev {
  def chance(percent: Double)(block: => Unit) {
    if (scala.math.random < percent) {
      block
    }
  }
}