package com.taintech.immobili.example.akka

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props, Terminated}
import akka.routing.RoundRobinPool

object Main2 {

  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Hello")
    val roundRouter = system.actorOf(RoundRobinPool(5).props(RoundActor.props), "roundRouter")
    (0 to 7).foreach(x => roundRouter!"hello")
    val a = system.actorOf(Props[HelloWorld], "helloWorld")
    system.actorOf(Props(classOf[Terminator], a), "terminator")
  }

  class Terminator(ref: ActorRef) extends Actor with ActorLogging {
    context watch ref
    def receive = {
      case Terminated(_) =>
        log.info("{} has terminated, shutting down system", ref.path)
        context.system.shutdown()
    }
  }

}