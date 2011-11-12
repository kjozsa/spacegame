name := "scalaship"

version := "0.1"

libraryDependencies ++= Seq(
    "junit" % "junit" % "4.8.2",
    "org.scalatest" % "scalatest_2.9.0" % "1.6.1",
    "org.mockito" % "mockito-all" % "1.8.5",
    "org.lwjgl.lwjgl" % "lwjgl" % "2.8.1"
    )

scalacOptions += "-deprecation"

scalaVersion := "2.9.0"

