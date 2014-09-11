package com.taintech.immobili.example

import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver

import scala.collection.JavaConverters._
/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/11/2014
 * Time: 11:28 AM
 */
object GoogleSuggest extends App{
  val driver = new FirefoxDriver()
  driver.get("http://www.google.com/webhp?complete=1&hl=en")
  val query = driver.findElement(By.name("q"))
  query.sendKeys("Cheese")
  Thread.sleep(5000)
  val allSuggestions = driver.findElements(By.xpath("//td[@class='gssb_a gbqfsf']")).asScala
  allSuggestions.foreach(e => println(e.getText))
  driver.quit()
}
