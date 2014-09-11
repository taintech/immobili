package com.taintech.immobili.example.selenium

import org.openqa.selenium.By
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import scala.collection.JavaConverters._

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/11/2014
 * Time: 10:45 AM
 */
object Example {
  def main(args: Array[String]): Unit = {
    val driver = new HtmlUnitDriver()
    driver.get("http://www.google.com")
    val element = driver.findElement(By.name("q"))
    element.sendKeys("Cheese!")
    element.submit()
    val results = driver.findElementsByCssSelector(".g").asScala
    results.foreach { e =>
      Console println e.findElement(By.tagName("h3")).getText
    }
    driver.quit()
  }
}
