
abstract class Animal {
  def name: String
}

case class Cat(name: String) extends Animal

case class Dog(name: String) extends Animal

abstract class Printer[-T] {
  def print(value: T)
}

class AnimalPrinter extends Printer[Animal] {
  override def print(animal: Animal): Unit = println(s"The animal's name is ${animal.name}")
}

class CatPrinter extends Printer[Cat] {
  override def print(cat: Cat): Unit = println(s"The cat's name is ${cat.name}")
}

object CovarianceExample extends App {

  def printAnimals(animals: List[Animal]) = {
    animals foreach {
      animal => println(animal)
    }
  }

  val cats: List[Cat] = List(Cat("Tom"), Cat("Black"))
  val dogs: List[Dog] = List(Dog("Rex"), Dog("White"))

  printAnimals(cats)
  printAnimals(dogs)
}

object ContravarianceExample extends App {
  val myCat = Cat("Felix")

  def printMyCat(printer: Printer[Cat]): Unit = {
    printer.print(myCat)
  }

  val catPrinter = new CatPrinter
  val animalPrinter = new AnimalPrinter

  printMyCat(catPrinter)
  printMyCat(animalPrinter)

  val myDog = new Dog("Rex")

  // catPrinter.print(myDog)
  animalPrinter.print(myDog)
}
