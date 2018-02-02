package example

import scala.collection.mutable

package v1 {

  class Point(val x: Int, val y: Int) {
    def equals(that: Point): Boolean = this.x == that.x && this.y == that.y
  }

  object TestEquality extends App {

      val p1, p2 = new Point(1, 2)
      // true
      println(p1 equals p2)

      val p2a: Any = p2
      // false
      println(p1 equals p2a)

      val set = mutable.HashSet(p1)
      // false
      println(set contains p2)
  }

}

package v2 {

  class Point(val x: Int, val y: Int) {
    override def equals(other: Any): Boolean = other match {
      case that: Point => this.x == that.x && this.y == that.y
      case _ => false
    }
  }

  object TestEquality extends App {

    val p1, p2 = new Point(1, 2)
    // true
    println(p1 equals p2)

    val p2a: Any = p2
    // true
    println(p1 equals p2a)

    val set = mutable.HashSet(p1)
    // false
    println(set contains p2)
  }

}

package v3 {
  class Point(val x: Int, val y: Int) {
    override def hashCode(): Int = 41 * (41 + x) + y

    override def equals(other: Any): Boolean = other match {
      case that: Point => this.x == that.x && this.y == that.y
      case _ => false
    }
  }

  object TestEquality extends App {

    val p1, p2 = new Point(1, 2)
    // true
    println(p1 equals p2)

    val p2a: Any = p2
    // true
    println(p1 equals p2a)

    val set = mutable.HashSet(p1)
    // true
    println(set contains p2)
  }

}