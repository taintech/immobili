package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.routing.RoundRobinPool

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 7:41 PM
 */
class Manager extends Actor with ActorLogging{
  
  private var count = 0

  override def preStart() = {
    log.info("Starting crawler...")
    val crawler = context.actorOf(RoundRobinPool(10).props(Crawler.props), "crawler")
    log.info("Started crawler.")
    val parser: ActorRef = context.actorOf(Parser.props(self, crawler, ???), "parser")
    parser ! Parser.Start
  }

  override def receive = {
    case Parser.Started => increment()
    case Parser.Done if count > 1 => decrement()
    case Parser.Done =>
      log.info("Done message from parser.")
      context.stop(self)
  }

  private def increment() = count = count + 1
  private def decrement() = count = count - 1
}