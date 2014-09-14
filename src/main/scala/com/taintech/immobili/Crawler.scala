package com.taintech.immobili

import akka.actor.{ActorRef, Actor}
import com.taintech.immobili.Crawler.Parser
import com.taintech.immobili.db.Keeper
import org.openqa.selenium.htmlunit.HtmlUnitDriver

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 4:03 PM
 */
class Crawler(driver: HtmlUnitDriver, keeper: ActorRef, parser: Parser) extends Actor{
  import com.taintech.immobili.Crawler._

  override def receive = {
    case Request(url) => request(url)
  }

  def request(url: String): Unit ={
    driver.get(url)
    parser.consume(driver) match {
      case Page(data, callbacks) =>
        keeper ! data
        callbacks.foreach(self!_)
    }
  }

  override def postStop(){
    driver.quit()
  }
}

object Crawler {
  trait Parser {
    def consume(driver: HtmlUnitDriver):Page
  }
  case class Request(url: String)
  case class Page(data: Keeper.Record, callbacks: Seq[Request])
}
