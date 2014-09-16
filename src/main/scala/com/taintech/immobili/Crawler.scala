package com.taintech.immobili

import akka.actor.{Props, ActorRef, Actor}
import com.taintech.immobili.Crawler.Parser
import com.taintech.immobili.db.Keeper
import org.openqa.selenium.htmlunit.HtmlUnitDriver

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 4:03 PM
 */
class Crawler(driver: HtmlUnitDriver) extends Actor {
  import com.taintech.immobili.Crawler._
  def receive: Unit = {
    case Request(url, script, parser) =>
      driver.get(url)
      driver.executeAsyncScript(script)
      parser ! driver.getPageSource
  }
  override def postStop(): Unit ={
    driver.quit()
  }
}

object Crawler {
  case class Request(url: String, script: String, parser: ActorRef)
  def props: Props = Props(new Crawler({
    println("create driver")
    new HtmlUnitDriver()
  }))
}
