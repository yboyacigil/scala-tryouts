name := "scala-tryouts"

version := "1.0"

scalaVersion := "2.12.3"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.0-RC1"
        