package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.taintech.immobili.krisha.Crawler.{Request, Response}
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
      ListRootUrls.foreach{ url =>
        crawler ! Request(url, Listing)
        manager ! Started
      }
    case Response(Request(url, pageType), body) => pageType match {
      case Listing => ???
      case Profile => ???
    }
  }

  private def next(url: String) = {
    val nextPage = url.replaceFirst(".*page=", "").toInt
    url.replaceFirst("page=\\d*", s"page=$nextPage")
  }
}

object Parser {
  def props(manager: ActorRef, crawler: ActorRef): Props = Props(new Parser(manager, crawler))
  object Start
  object Started
  object Done
  object PageType extends Enumeration{
    type PageType = Value
    val Listing, Profile = Value
  }
  val Root = "http://krisha.kz"
  val Actions = List("arenda", "prodazha")
  val Cities = List("astana", "almaty")
  val Types = List("kvartiry", "doma", "dachi", "uchastkov", "ofisa")
  val ListRootUrls = for {
    action <- Actions
    category <- Types
    city <- Cities
  } yield s"$Root/$action/$category/$city/?page=1"

}