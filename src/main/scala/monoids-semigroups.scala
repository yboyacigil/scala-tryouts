package `monoids-semigroups` {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  object MonoidLaws {

    def associativeLaw[A](x: A, y: A, z: A)(implicit m: Monoid[A]): Boolean =
      m.combine(x, m.combine(y, z)) == m.combine(m.combine(x, y), z)

    def identityLaw[A](x: A)(implicit m: Monoid[A]): Boolean =
      (m.combine(x, m.empty) == x) && (m.combine(m.empty, x) == x)
  }

  object Monoid {
    def apply[A](implicit monoid: Monoid[A]) = monoid
  }


  object BooleanMonoids {
    implicit val booleanAndMonoid = new Monoid[Boolean] {
      def empty = true
      def combine(x: Boolean, y: Boolean) = x && y
    }

    implicit val booleanOrMonoid = new Monoid[Boolean] {
      def empty = false
      def combine(x: Boolean, y: Boolean) = x || y
    }

    implicit val booleanXorMonoid = new Monoid[Boolean] {
      def empty = false
      def combine(x: Boolean, y: Boolean) = (!x && y) || (x && !y)
    }

    implicit var booleanXnorMonoid = new Monoid[Boolean] {
      def empty = true
      def combine(x: Boolean, y: Boolean) = (!x || y) && (x || !y)
    }
  }

  object SetMonoids {
    implicit def unionMonoid[A]: Monoid[Set[A]] = new Monoid[Set[A]] {
      override def empty = Set.empty[A]

      override def combine(x: Set[A], y: Set[A]) = x.union(y)
     }

    implicit def intersectSemigroup[A]: Semigroup[Set[A]] = (x: Set[A], y: Set[A]) => x.intersect(y)
  }

  object SuperAdder {
    import cats.instances.int._
    import cats.instances.option._
    import cats.syntax.semigroup._

    def add(items: List[Int]): Int = items.foldLeft(cats.Monoid[Int].empty)(_ + _)

    def addOptions(items: List[Option[Int]]): Option[Int] = items.foldLeft(cats.Monoid[Option[Int]].empty)(_ |+| _)

    case class Order(totalCost: Double, quantity: Double)

    def add[A](items: List[A])(implicit m: cats.Monoid[A]): A = items.foldLeft(m.empty)(m.combine(_, _))

    implicit val orderMonoid = new cats.Monoid[Order] {
      override def empty = Order(0, 0)

      override def combine(x: Order, y: Order) = Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
    }
  }

}