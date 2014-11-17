package com.taintech.experiment.progfun

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 11/17/14
 * Time: 23:40
 */
class Pouring(capacity: Vector[Int]) {

  //States

  type State = Vector[Int]
  val initialState = capacity map (x => 0)

  //Moves

  trait Move {
    def change(state: State): State
  }

  case class Empty(glass: Int) extends Move {
    override def change(state: State): State = state updated(glass, 0)
  }

  case class Fill(glass: Int) extends Move {
    override def change(state: State): State = state updated(glass, capacity(glass))
  }

  case class Pour(from: Int, to: Int) extends Move {
    override def change(state: State): State = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated(from,  state(from) - amount) updated(to, state(to) + amount)
    }
  }

  val glasses = 0 until capacity.length

  val moves =
      (for (g <- glasses) yield Empty(g)) ++
      (for (g <- glasses) yield Fill(g)) ++
      (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))

  class Path(history: List[Move], val endState: State) {
    def extend(move: Move) = new Path(move :: history, move change endState)
    override def toString = (history.reverse mkString ",") + "--> " + endState
  }

  val initialPath = new Path(Nil, initialState)

  def from(paths: Set[Path], explored: Set[State]): Stream[Set[Path]] =
    if(paths.isEmpty) Stream.empty
    else {
      val more = for {
        path <- paths
        next <- moves map path.extend
        if !(explored contains next.endState)
      } yield next
      paths #:: from(more, explored ++ (more map (_.endState)))
    }

  def pathSets = from(Set(initialPath), Set(initialState))

  def solutions(target: Int): Stream[Path] =
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
}


object Pouring {
  def main(args: Array[String]): Unit = {
    val problem1 = new Pouring(Vector(4, 7))
    Console println "First: " + problem1.solutions(6)

    val problem2 = new Pouring(Vector(4, 9))
    Console println "First: " + problem2.solutions(6)
  }
}