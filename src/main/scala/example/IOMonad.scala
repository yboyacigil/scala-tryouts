package example

class IO[A] private (block: => A) {

  def run = block

  def map[B](f: A => B): IO[B] = flatMap(a => IO(f(a)))

  def flatMap[B](f: A => IO[B]): IO[B] = {
    val r1: IO[B] = f(run)
    val r2: B = r1.run
    IO(r2)
  }

}

object IO {
  def apply[A](block: => A): IO[A] = new IO(block)
}

object IOMonad extends App {

  def getLine: IO[String] = IO(scala.io.StdIn.readLine())

  def putLine(s: String): IO[Unit] = IO(println(s))

  val exp: IO[Unit] = for {
    _ <- putLine("First name?")
    first <- getLine
    _ <- putLine("Last name?")
    last <- getLine
    _ <- putLine(s"Hello, $first $last!")
  } yield ()

  exp.run

}
