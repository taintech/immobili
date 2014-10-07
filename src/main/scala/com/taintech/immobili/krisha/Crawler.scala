package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, Props}
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
    case req@Request(url, pageType) =>
      log.info(s"Get url $url.")
      driver.get(url)
      sender() ! Response(req, driver.getPageSource)
      driver.close()
  }

  override def postStop() = {
    driver.quit()
    log.info(s"Driver closed.")
  }

}

object Crawler{
  def props: Props = Props(new Crawler(new HtmlUnitDriver()))
  case class Request(url: String, pageType: PageType)
  case class Response(request: Request, body: String)
}

