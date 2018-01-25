package example

import scala.util.Try

object ErrorHandling extends App {

  def makeInt(s: String): Int = {
    s.trim.toInt
  }

  def makeIntOpt(s: String): Option[Int] = {
    try {
      Some(s.trim.toInt)
    } catch {
      case e: Exception => None
    }
  }

  def makeIntTry(s: String): Try[Int] = Try(s.trim.toInt)

  def makeIntEither(s: String): Either[String, Int] = {
    try {
      Right(s.trim.toInt)
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  println("""--- makeInt("a")""")
  try {
    println(makeInt("a"))
  } catch {
    case e: Exception => println(s"ERROR: ${e.getMessage}" )
  }

  println("""--- makeIntOpt("a")""")
  println(makeIntOpt("a"))

  println("""--- makeIntTry("a")""")
  println(makeIntTry("a"))

  println("""--- makeIntEither("a")""")
  println(makeIntEither("a"))
  
  // Option[Int] = Some(3)
  val x = for {
    a <- makeIntOpt("1")
    b <- makeIntOpt("2")
  } yield a + b
  
  // Try[Int] = Success(3)
  val y = for {
    a <- makeIntTry("1")
    b <- makeIntTry("2")
  } yield a + b
  
  // Either[String, Int] = Right(3)
  val z = for {
    a <- makeIntEither("1")
    b <- makeIntEither("2")
  } yield a + b

}

/*
Scala REPL

scala> val x = example.ErrorHandling
x: example.ScalaErrorHandling.type = example.ScalaErrorHandling$@718b7c74

scala> x.makeInt("a")
java.lang.NumberFormatException: For input string: "a"
  at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
  at java.lang.Integer.parseInt(Integer.java:580)
  at java.lang.Integer.parseInt(Integer.java:615)
  at scala.collection.immutable.StringLike.toInt(StringLike.scala:301)
  at scala.collection.immutable.StringLike.toInt$(StringLike.scala:301)
  at scala.collection.immutable.StringOps.toInt(StringOps.scala:29)
  at example.ScalaErrorHandling.makeInt(ScalaErrorHandling.scala:8)
  ... 39 elided

scala> x.makeIntOpt("a")
res1: Option[Int] = None

scala> x.makeIntTry("a")
res2: scala.util.Try[Int] = Failure(java.lang.NumberFormatException: For input string: "a")

scala> x.makeIntEither("a")
res3: Either[String,Int] = Left(For input string: "a")

scala> for { a <- x.makeIntOpt("2"); b <- x.makeIntOpt("a") } yield a + b
res8: Option[Int] = None

scala> for { a <- x.makeIntTry("2"); b <- x.makeIntTry("a") } yield a + b
res9: scala.util.Try[Int] = Failure(java.lang.NumberFormatException: For input string: "a")

scala> for { a <- x.makeIntEither("2"); b <- x.makeIntEither("a") } yield a + b
res10: scala.util.Either[String,Int] = Left(For input string: "a")

*/
