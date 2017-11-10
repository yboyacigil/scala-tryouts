package traits {

  trait Iterator[T] {
    def hasNext: Boolean
    def next: T
  }

  class IntIterator(to: Int) extends Iterator[Int] {
    private var current = 0

    override def hasNext: Boolean = current < to

    override def next: Int = {
      if (hasNext) {
        val t = current
        current += 1
        t
      } else 0
    }
  }

  object Traits extends App {

    val iterator = new IntIterator(10)
    println(iterator.next)
    println(iterator.next)

  }
}
