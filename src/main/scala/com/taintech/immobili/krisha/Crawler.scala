package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, Props}
import com.taintech.immobili.krisha.Parser.Page
import com.taintech.immobili.krisha.Parser.PageType.PageType
import org.openqa.selenium.htmlunit.HtmlUnitDriver


/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 4:03 PM
 */
class Crawler(driver: HtmlUnitDriver) extends Actor  with ActorLogging{
  import com.taintech.immobili.krisha.Crawler._

  override def receive = {
    case Request(url, pageType) =>
      log.info(s"Get url $url.")
      driver.get(url)
      sender() ! Page(driver.getPageSource, pageType)
  }

}

object Crawler{
  def props(driver: HtmlUnitDriver): Props = Props(new Crawler(driver))
  case class Request(url: String, pageType: PageType)
}

