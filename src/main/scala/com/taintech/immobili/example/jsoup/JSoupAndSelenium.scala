package com.taintech.immobili.example.jsoup

import org.jsoup.Jsoup
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import scala.collection.JavaConverters._

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/14/2014
 * Time: 11:00 PM
 */
object JSoupAndSelenium {
  def main(args: Array[String]): Unit ={
    val driver = new HtmlUnitDriver()
    driver.get("http://krisha.kz/")
    val doc = Jsoup.parse(driver.getPageSource)
    doc.select(".titleBlock").asScala.foreach(println)
    driver.quit()
  }
}
