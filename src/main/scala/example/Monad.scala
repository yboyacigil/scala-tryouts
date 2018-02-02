package example

case class Wrapper[A](value: A) {

  def map[B](f: A => B): Wrapper[B] = {
    Wrapper(f(value))
  }

  def flatMap[B](f: A => Wrapper[B]): Wrapper[B] = f(value)

}

case class Debuggable[A](value: A, messages: Vector[String]) {
  def map[B](f: A => B): Debuggable[B] = {
    Debuggable(f(value), messages)
  }

  def flatMap[B](f: A => Debuggable[B]): Debuggable[B] = {
    val next = f(value)
    Debuggable(next.value, messages ++ next.messages)
  }
}

object ForExpressionsWrappedTypes extends App {

  // yields Wrapper(6)
  val x: Wrapper[Int] = for {
    a <- Wrapper(1)
    b <- Wrapper(2)
    c <- Wrapper(3)
  } yield a + b + c

  // yields Debuggable(6, Vector("value is 1", "value is 2", ...)
  val y: Debuggable[Int] = for {
    a <- Debuggable(1, Vector("value is 1"))
    b <- Debuggable(2, Vector("value is 2"))
    c <- Debuggable(3, Vector("value is 3"))
  } yield a + b + c
}