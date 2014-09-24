package com.taintech.experiments.cake

class Production extends Dummy with A with B {

}

class Dummy
case class Field(injected: InjectField)
case class InjectField(field: Field)
trait A extends AA {
  b: B =>
  lazy val field: Field = new Field(fieldToInject)
}
trait AA {
  def field: Field
}

trait B extends BB {
  a: A =>
  lazy  val fieldToInject: InjectField = new InjectField(field)
}
trait BB {
  def fieldToInject: InjectField
}

object Main{
  def main(args: Array[String]): Unit ={
    val prod = new Production
    val b: B = prod
    val a: A = prod
    Console  println b.fieldToInject
    Console  println a.field
  }
}
