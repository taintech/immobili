object a {

  val t1 = new NonEmpty(3)
  val t2 = t1.incl(2)
  val t3 = t2.incl(6).incl(0)


  val tt1 = new NonEmpty(11).incl(4).incl(7)

  val u1 = t3 union tt1
}


abstract class IntSet{
  def contains(x: Int): Boolean
  def incl(x: Int): IntSet
  def union(other: IntSet): IntSet
}

object Empty extends IntSet{

  override def contains(x: Int): Boolean = false

  override def incl(x: Int): IntSet = new NonEmpty(x , Empty, Empty)

  override def union(other: IntSet): IntSet = other

  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet{

  def this(x: Int) = this(x, Empty, Empty)

  override def contains(x: Int): Boolean =
    if(x < elem) left contains x
    else if(x > elem) right contains x
    else true

  override def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if(x > elem) new NonEmpty(elem, left, right incl x)
    else this

  override def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem

  override def toString = s"{$left$elem$right}"

}


