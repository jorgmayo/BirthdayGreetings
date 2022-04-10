ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.7"

lazy val root = (project in file("."))
  .settings(
    name := "birthday-greetings"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test
