package example

case class State[S, A](run: S => (S, A)) {

  def flatMap[B](f: A => State[S, B]): State[S, B] = State { (s0: S) =>
    val (s1, a) = run(s0)
    f(a).run(s1)
  }

  def map[B](f: A => B): State[S, B] = flatMap(a => State.point(f(a)))
}

object State {
  def point[S, A](v: A): State[S, A] = State(s => (s, v))
}

object Golfing extends App {

  case class GolfState(distance: Int)

  def swing(distance: Int): State[GolfState, Int] = State { s: GolfState =>
    val newDistance = s.distance + distance
    (GolfState(newDistance), newDistance)
  }

  val stateWithNewDistance: State[GolfState, Int] = for {
    _ <- swing(20)
    _ <- swing(15)
    total <- swing(0)
  } yield total

  val result:(GolfState, Int) = stateWithNewDistance.run(GolfState(0))

  println(s"Golf state: ${result._1}")
  println(s"Total distance: ${result._2}")


}
