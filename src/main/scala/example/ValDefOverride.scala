package example

class Base {
  val valProp = { println("valProp init"); 0 }

  def defProp = { println("defProp init"); 0 }

  def defPropWithParenthesis() = { println("defPropWithParenthesis init"); 0}

}

class ExtendedOverridingWithVal extends Base {

  override val valProp = 1

  override val defProp = 1

  override val defPropWithParenthesis = 1
}

class ExtendedOverridingWithDef extends Base {

  // Not works!
  // override def valProp = 1
  override val valProp = 1

  override def defProp = 1

  override def defPropWithParenthesis = 1
}

/*
Scala REPL

New Base instance:
  scala> val b = new example.Base
  valProp init
  b: example.Base = example.Base@f2d4af1

  scala> b.valProp
  res0: Int = 0

  scala> b.defProp
  defProp init
  res1: Int = 0

  scala> b.defProp
  defProp init
  res2: Int = 0

  scala> b.defPropWithParenthesis
  defPropWithParenthesis init
  res3: Int = 0

  scala> b.defPropWithParenthesis()
  defPropWithParenthesis init
  res4: Int = 0

New ExtendedOverridingWithVal instance:
  scala> val e1 = new example.ExtendedOverridingWithVal
  valProp init
  e1: example.ExtendedOverridingWithVal = example.ExtendedOverridingWithVal@579fddab

  scala> e1.valProp
  res5: Int = 1

  scala> e1.defProp
  res6: Int = 1

  scala> e1.defPropWithParenthesis
  res7: Int = 1

  scala> e1.defPropWithParenthesis()
  <console>:16: error: Int does not take parameters
         e1.defPropWithParenthesis()

New ExtendedOverridingWithVal instance:
  scala> val e2 = new example.ExtendedOverridingWithDef
  valProp init
  e2: example.ExtendedOverridingWithDef = example.ExtendedOverridingWithDef@70e89a2a

  scala> e2.valProp
  res8: Int = 1

  scala> e2.defProp
  res9: Int = 1

  scala> e2.defPropWithParenthesis
  res10: Int = 1

  scala> e2.defPropWithParenthesis()
  res11: Int = 1
 */