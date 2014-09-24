package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.taintech.immobili.krisha.Crawler.Request
import com.taintech.immobili.krisha.Parser.PageType.PageType
import org.jsoup.Jsoup

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 6:26 PM
 */
class Parser(manager: ActorRef, crawler: ActorRef) extends Actor with ActorLogging{
  import com.taintech.immobili.krisha.Parser.PageType._
  import com.taintech.immobili.krisha.Parser._

  override def receive = {
    case Start =>
      log.info("Received start message.")
      crawler ! Request("http://krisha.kz/arenda/kvartiry/astana/", Listing)
    case Page(body, pageType) =>
      val first = Jsoup.parse(body).select(".title").first().select("a").first().text()
      log.info(s"Parsed first $first")
      manager ! Done
  }

}

object Parser {
  def props(manager: ActorRef, crawler: ActorRef): Props = Props(new Parser(manager, crawler))
  object Start
  object Done
  object PageType extends Enumeration{
    type PageType = Value
    val Listing, Profile = Value
  }
  case class Page(body: String, pageType: PageType)
}