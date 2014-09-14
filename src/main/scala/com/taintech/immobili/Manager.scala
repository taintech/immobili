package com.taintech.immobili

import akka.actor.{Props, Actor}
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
    val driver = new HtmlUnitDriver()
    val keeper = context.actorOf(Props[Keeper], "keeper")
    val parser = new Listing
    val crawler = context.actorOf(Props(classOf[Crawler], driver, keeper, parser), "crawler")
    crawler ! new Request("http://krisha.kz/prodazha/kvartiry/astana/")
  }

  override def receive = {
    case End => context.stop(self)
  }
}

object Manager{
  object Start
  object End
}
