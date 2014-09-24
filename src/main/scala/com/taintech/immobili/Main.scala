package com.taintech.immobili

import akka.actor._
import com.taintech.immobili.krisha.Manager

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/25/14
 * Time: 1:12
 */
object Main {
  def main(args: Array[String]): Unit ={
    val system = ActorSystem("KrishaCrawler")
    val manager = system.actorOf(Props[Manager], "manager")
    system.actorOf(Props(classOf[Terminator], manager), "terminator")
  }

  class Terminator(ref: ActorRef) extends Actor with ActorLogging {
    context watch ref
    def receive = {
      case Terminated(_) =>
        log.info("Terminated message, shutting down system.", ref.path)
        context.system.shutdown()
    }
  }
}
