package org.scrawler.website.extractor

import java.net.URL

import org.openqa.selenium.{By, WebDriver}

import scala.collection.JavaConverters._

// thanks Alvin: http://alvinalexander.com/scala/how-to-use-java-style-logging-slf4j-scala
import org.slf4j.LoggerFactory

// big thanks to https://joshrendek.com/2013/10/parsing-html-in-scala/ for the HTML parsing stuff
import org.openqa.selenium.WebElement

object LinkExtractor {
  private val logger = LoggerFactory.getLogger(this.getClass)

  def apply(website: URL)(implicit driver: WebDriver): Set[URL] = {
    if (isObviouslyNotHTML(website)) return Set.empty

    try {
      driver.get(website.toString)
    }

    catch {
      case e: java.lang.Exception => return Set(new URL("http://default.com"))
    }

    logger.info("Currently scrawling " + website)

    try {
      driver.findElements(By.xpath("//a")).asScala.toSet.map({ element: WebElement =>
        try {
          new URL(element.getAttribute("href"))
        }

        catch {
          case e: java.net.MalformedURLException => new URL("http://default.com")
        }
      })
    }

    catch {
      case e: java.lang.IllegalStateException => Set(new URL("http://default.com"))
    }
  }

  private def isObviouslyNotHTML(website: URL): Boolean = {
    val websiteString = website.toString
    websiteString.contains("mailto") ||
      websiteString.contains(".png") ||
      websiteString.contains(".jpeg") ||
      websiteString.contains(".pdf") ||
      websiteString.contains(".txt")
  }
}

