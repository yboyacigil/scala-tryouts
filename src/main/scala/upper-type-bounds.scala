package `upper-type-bounds` {

  abstract class Animal {
    def name: String
  }

  abstract class Pet extends Animal {}

  class Cat extends Pet {
    override def name: String = "Cat"
  }

  class Dog extends Pet {
    override def name: String = "Dog"
  }

  class Lion extends Animal {
    override def name: String = "Lion"
  }

  class PetContainer[P <: Pet](p: P) {
    def pet: P = p
  }

  object Main extends App {
    new PetContainer[Dog](new Dog)
    new PetContainer[Cat](new Cat)

    // Does not compile
    // new PetContainer[Lion](new Lion)
  }
}
