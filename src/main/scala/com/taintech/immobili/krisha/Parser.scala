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
class Parser(manager: ActorRef, crawler: ActorRef, keeper: ActorRef) extends Actor with ActorLogging{
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
      case Listing => parseList(body) match {
        case ParsedList(next, summaries, urls) =>
          summaries.foreach(s => keeper ! s)
          urls.foreach(u => crawler ! Request(u, Profile))
          next.foreach(n => crawler ! Request(n, Profile))
          if (next.isEmpty) manager ! Done
      }
      case Profile => keeper ! parseBody(body)
    }
  }

  private def next(url: String) = {
    val nextPage = url.replaceFirst(".*page=", "").toInt
    url.replaceFirst("page=\\d*", s"page=$nextPage")
  }
}

object Parser {
  def props(manager: ActorRef, crawler: ActorRef, keeper: ActorRef): Props = Props(new Parser(manager, crawler, keeper))
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

  def parseBody(body: String): String = ???                //TODO

  case class ParsedList(next: Option[String], summaries: List[String], profileUrls: List[String])
  def parseList(body: String): ParsedList = ParsedList(None, List("hello world"), List.empty) //TODO
}