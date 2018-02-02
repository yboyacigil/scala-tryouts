name := "scala-tryouts"

version := "1.0"

scalaVersion := "2.12.3"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies ++= Seq("org.typelevel" %% "cats-core" % "1.0.0-RC1",
  "org.typelevel" %% "cats-effect" % "0.5")


initialCommands in console := ""