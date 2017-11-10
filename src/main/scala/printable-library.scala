package `printable-library` {

  trait Printable[A] {

    def format(a: A): String
  }

  object PrintableInstances {

    implicit val stringPrintable = new Printable[String] {
      override def format(a: String) = a
    }

    implicit val intPrintable = new Printable[Int] {
      override def format(a: Int) = a.toString
    }

    implicit val catPrintable = new Printable[Cat] {
      override def format(a: Cat) = {
        val name = Printable.format(a.name)
        val age = Printable.format(a.age)
        val color = Printable.format(a.color)

        s"$name is a $age year-old $color cat"
      }
    }
  }

  object Printable {

    def format[A](value: A)(implicit converter: Printable[A]): String = converter.format(value)

    def print[A](value: A)(implicit converter: Printable[A]): Unit = println(format(value))
  }

  final case class Cat(name: String, age: Int, color: String)

  object PrintableSyntax {

    implicit class PrintOps[A](a: A) {

      def format(implicit p: Printable[A]): String = {
        p.format(a)
      }

      def print(implicit p: Printable[A]): Unit = {
        println(format(p))
      }

    }

  }

}



