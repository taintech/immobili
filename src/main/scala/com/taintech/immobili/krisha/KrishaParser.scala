package com.taintech.immobili.krisha

import com.taintech.immobili.Crawler
import com.taintech.immobili.Crawler._
import org.openqa.selenium.htmlunit.HtmlUnitDriver

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 6:26 PM
 */
abstract class KrishaParser extends Parser

class Listing extends KrishaParser {
  override def consume(driver: HtmlUnitDriver): Crawler.Page = ???
}

class Page extends KrishaParser {
  override def consume(driver: HtmlUnitDriver): Crawler.Page = ???
}