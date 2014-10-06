package com.taintech.immobili.krisha

import akka.actor.Actor

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 10/7/14
 * Time: 1:27
 */
class Keeper extends Actor {

  override def receive = {
    case s: String => Console println s //TODO
  }

}
