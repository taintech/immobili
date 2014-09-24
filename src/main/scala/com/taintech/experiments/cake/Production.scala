package com.taintech.experiments.cake

class Production extends Dummy with A with B {

}

class Dummy
case class Field(injected: InjectField)
case class InjectField(field: Int)
trait A extends AA {
  b: B =>
  lazy val field = new Field(fieldToInject)
}
trait AA {
  def field: Field
}

trait B extends BB {
  lazy  val fieldToInject = new InjectField(42)
}
trait BB {
  def fieldToInject: InjectField
}

object Main{
  def main(args: Array[String]): Unit ={
    val prod = new Production
    val a: A = prod
    Console  println a.field
  }
}
