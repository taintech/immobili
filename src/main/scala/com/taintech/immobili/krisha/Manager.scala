package com.taintech.immobili.krisha

import akka.actor.{Actor, ActorLogging, ActorRef}
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import scala.concurrent.duration._

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 7:41 PM
 */
class Manager extends Actor with ActorLogging{

  override def preStart() = {
    log.info("[Manager] starting crawler...")
    val driver = new HtmlUnitDriver()
    val crawler = context.actorOf(Crawler.props(driver), "crawler")
    log.info("[Manager] started crawler.")
    val parser: ActorRef = context.actorOf(Parser.props(self, crawler), "parser")
    parser ! Parser.Start
    //    implicit val executionContext = context.system.dispatcher
//    context.system.scheduler.scheduleOnce(100 milliseconds, parser, Parser.Start)
  }

  override def receive = {
    case Parser.Done =>
      log.info("[Manger] done message from parser.")
      context.stop(self)
  }
}