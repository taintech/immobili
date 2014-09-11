package com.taintech.immobili.example.selenium

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.RemoteWebDriver

import scala.collection.JavaConverters._
import scala.concurrent._

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 9/11/2014
 * Time: 11:28 AM
 */
object GoogleSuggest{
  import scala.concurrent.ExecutionContext.Implicits.global
  def main(args: Array[String]): Unit ={
    val browser1 = cheese(new ChromeDriver())
    val browser2 = cheese(new FirefoxDriver())
    browser1 onSuccess {
      case _ => Console println "browser 1 finished"
    }
    browser2 onSuccess {
      case _ => Console println "browser 2 finished"
    }
  }

  def cheese(driver: RemoteWebDriver): Future[Unit] = Future {
    driver.get("http://www.google.com/webhp?complete=1&hl=en")
    val query = driver.findElement(By.name("q"))
    query.sendKeys("Cheese")
    val allSuggestions = driver.findElements(By.xpath("//div[@class='g']")).asScala
    allSuggestions.foreach(e => println(e.getText))
    driver.quit()
  }
}
