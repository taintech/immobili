package com.taintech.immobili.db

import akka.actor.Actor

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 4:32 PM
 */
class Keeper extends Actor {
  import com.taintech.immobili.db.Keeper._

  override def receive = {
    case Record(data) => ???
  }
}

object Keeper {
  case class Record(data: String)
}