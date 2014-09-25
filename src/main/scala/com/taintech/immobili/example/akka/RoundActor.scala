package com.taintech.immobili.example.akka

import akka.actor.{ActorLogging, Props, Actor}

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/25/14
 * Time: 8:13
 */
class RoundActor extends Actor with ActorLogging{
  override def receive = {
    case "hello" => log.info("hi")
  }
}

object RoundActor{
  def props: Props = Props({
    Console println "Round props!"
    new RoundActor
  })
}
