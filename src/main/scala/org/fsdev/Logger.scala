/**
 *
 */
package org.fsdev

import java.util.logging.{ Logger => J4Logger }
import java.util.logging.Level
import java.util.logging.Filter
import java.util.logging.LogManager
import java.io.InputStreamReader
import java.io.StringReader
import java.io.ByteArrayInputStream

/**
 * @author kjozsa
 */
trait Logger {
  //  LogManager.getLogManager().readConfiguration(new ByteArrayInputStream("""
  //		  .level = ALL
  //		  handlers = java.util.logging.ConsoleHandler.level = ALL
  //		  java.util.logging.ConsoleHandler.level = ALL
  //  """.getBytes))

  val logger = J4Logger.getLogger("main")
  logger.setLevel(Level.FINEST)

  implicit def double2String(x: Double) = x.toString()

  def debug = logger.info _
  def info = logger.info _
  def warn = logger.warning _
  def error = logger.severe _
}