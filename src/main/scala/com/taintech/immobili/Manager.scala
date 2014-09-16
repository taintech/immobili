package com.taintech.immobili

import akka.actor.{Props, Actor}
import akka.routing.RoundRobinPool
import com.taintech.immobili.Crawler.Request
import com.taintech.immobili.db.Keeper
import com.taintech.immobili.krisha.Listing
import org.openqa.selenium.htmlunit.HtmlUnitDriver

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 7:41 PM
 */
class Manager extends Actor{
  import Manager._
  override def preStart(): Unit ={
    val crawler = context.actorOf(RoundRobinPool(NUMBER_OF_CRAWLERS).props(Crawler.props), "crawler")
  }
  override def receive: Unit ={
    case FINISH => context.stop(self)
  }
}

object Manager {
  object FINISH
  val NUMBER_OF_CRAWLERS = 5
}
