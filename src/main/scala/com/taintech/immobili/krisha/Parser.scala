package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.taintech.immobili.krisha.Crawler.{Request, Response}
import org.joda.time.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, Document}
import org.jsoup.select.Elements

import scala.collection.JavaConverters._

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 6:26 PM
 */
class Parser(manager: ActorRef, crawler: ActorRef, keeper: ActorRef) extends Actor with ActorLogging{
  import com.taintech.immobili.krisha.Parser.PageType._
  import com.taintech.immobili.krisha.Parser._

  val today = LocalDate.now()
  val old = today.minusDays(1)

  override def receive = {
    case Start =>
      log.info("Received start message.")
      ListRootUrls.foreach{ url =>
        crawler ! Request(url, Listing)
        manager ! Started
      }
    case Response(Request(url, pageType), body) => pageType match {
      case Listing => parseList(body, url) match {
        case ParsedList(next, summaries, urls) =>
          summaries.foreach(s => keeper ! s)
          urls.foreach(u => crawler ! Request(u, Profile))
          next.foreach(n => crawler ! Request(n, Listing))
          if (next.isEmpty) manager ! Done
      }
      case Profile => keeper ! parseBody(body)
    }
  }


  private[this] def parseBody(body: String): String = ???  //TODO


  private[this] def itemSummaries(doc: Document) = doc.select(".descr")

  private[this] def nextOpt(elements: List[Element], url: String): Option[String] = try {
    if(elements.exists(outdated)) None
    else Some(next(url))
  } catch{
    case e: Exception =>
      log.error(e, "Achtung!")
      None
  }

  private[this] def outdated(element: Element): Boolean =
    element.select("span.gray").last().text.startsWith(old.dayOfMonth().get().toString)

  private[this] def profileUrls(elements: List[Element]): List[String] = List.empty //TODO

  private[this] def parseList(body: String, url: String) = {
    val doc = Jsoup.parse(body)
    val items = itemSummaries(doc)
    ParsedList(nextOpt(items, url), items, profileUrls(items))
  }

  private[this] def next(url: String) = {
    val nextPage = url.replaceFirst(".*page=", "").toInt + 1
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
  val Types = List("kvartiry")
  val ListRootUrls = for {
    action <- Actions
    category <- Types
    city <- Cities
  } yield s"$Root/$action/$category/$city/?page=1"

  case class ParsedList(next: Option[String], summaries: List[String], profileUrls: List[String])

  def toStringList(elements: Elements) = elements.asScala.map(_.html()).toList
  def toElementList(elements: Elements) = elements.asScala.toList
  implicit def elementsToStringList(elements: Elements): List[String] = toStringList(elements)
  implicit def elementsToElementList(elements: Elements): List[Element] = toElementList(elements)
}