package monads {

  object Sequencing {

    def parseInt(str: String): Option[Int] = {
      scala.util.Try(str.toInt).toOption
    }

    def divide(a: Int, b: Int): Option[Int] = {
      if (b == 0) None else Some(a / b)
    }

    def stringDivideBy(aStr: String, bStr: String): Option[Int] =
      for {
        aNum <- parseInt(aStr)
        bNum <- parseInt(bStr)
        ans  <- divide(aNum, bNum)
      } yield ans
  }

  trait Monad[F[_]] {
    def pure[A](value: A): F[A]

    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B]

    def map[A, B](value: F[A])(func: A => B): F[B] = flatMap(value)(a => pure(func(a)))
  }

  import cats.Id
  object IdMonad {

    def pure[A](value: A): Id[A] = value

    def map[A, B](initial: Id[A])(func: A => B): Id[B] = func(initial)

    def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] = func(initial)
  }

  import cats.Eval
  object EvalTests {

    def factorial(n: BigInt): Eval[BigInt] = {
      if (n == 1) {
        Eval.now(n)
      } else {
        Eval.defer(factorial(n - 1).map(_ * n))
      }
    }

    def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
      foldRightInternal(as, Eval.now(acc)) { (a, b) =>
        b.map(fn(a, _))
      }.value

    private def foldRightInternal[A, B](as: List[A], acc: Eval[B])(fn: (A, Eval[B]) => Eval[B]): Eval[B] =
      as match {
        case head::tail =>
          Eval.defer(fn(head, foldRightInternal(tail, acc)(fn)))
        case Nil =>
          acc
      }
  }

}
