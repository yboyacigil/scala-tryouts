package functors {

  import cats.Functor

  // map
  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A])
    extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

    def leaf[A](value: A): Tree[A] = Leaf(value)
  }

  // contramap
  trait Printable[A] {self =>
    def format(value: A): String

    def contramap[B](func: B => A): Printable[B] = (value: B) => self.format(func(value))
  }

  object Printable {
    def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)
  }

  final case class Box[A](value: A)

  // imap
  trait Codec[A] { self =>

    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B =>A): Codec[B] = {
      new Codec[B] {
        override def encode(value: B): String = self.encode(enc(value))

        override def decode(value: String): B = dec(self.decode(value))
      }
    }
  }

  object Codec {

    def encode[A](value: A)(implicit c: Codec[A]) = c.encode(value)

    def decode[A](value: String)(implicit c: Codec[A]) = c.decode(value)
  }


  object Instances {

    implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
      override def map[A, B](tree: Tree[A])(func: A => B): Tree[B] = {
        tree match {
          case Branch(left, right) =>
            Branch(map(left)(func), map(right)(func))
          case Leaf(value) =>
            Leaf(func(value))
        }
      }
    }


    implicit val stringPrintable: Printable[String] = (value: String) => "\"" + value + "\""

    implicit val booleanPrintable: Printable[Boolean] = (value: Boolean) => if (value) "yes" else "yes"

    implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] = p.contramap[Box[A]](_.value)


    implicit val stringCodec: Codec[String] = new Codec[String] {

      override def encode(value: String): String = value

      override def decode(value: String): String = value
    }

    implicit val intCodec: Codec[Int] = stringCodec.imap[Int](_.toInt, _.toString)

    implicit val booleanCodec: Codec[Boolean] = stringCodec.imap[Boolean](_.toBoolean, _.toString)

    implicit val doubleCodes: Codec[Double] = stringCodec.imap[Double](_.toDouble, _.toString)

    implicit def boxCodec[A](implicit c: Codec[A]): Codec[Box[A]] = c.imap[Box[A]](Box(_), _.value)
  }


}
