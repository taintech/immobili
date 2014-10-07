package com.taintech.immobili.krisha

import akka.actor.{ActorLogging, Actor}

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 10/7/14
 * Time: 1:27
 */
class Keeper extends Actor with ActorLogging{

  override def receive = {
    case s: String => log.info(s.take(50))
  }

}
