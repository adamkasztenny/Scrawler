package org.scrawler.website.extractor

import java.net.URL

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scrawler.domain.WebPage

import scala.collection.JavaConverters._

object WebPageExtractor {
  def apply(url: URL)(implicit driver: WebDriver): WebPage = {
    val title = Option(driver.getTitle).getOrElse("")

    val bold = elements("b") ++ elements("strong")
    val keywords = bold ++ headers ++ title.split(" ").toSet
    val sanitizedKeywords = keywords
      .flatMap(_.split(" ").toSet)
      .map(_.toLowerCase)
      .map(_.replace(",", "").replace(".", ""))

    WebPage(title, url, body, sanitizedKeywords)
  }

  private def body(implicit driver: WebDriver): String = elements("body").headOption.getOrElse("")

  private def headers(implicit driver: WebDriver): Set[String] = (1 to 6).flatMap(i => elements(s"h$i")).toSet

  private def elements(tag: String)(implicit driver: WebDriver) = {
    try {
      driver.findElements(By.xpath("//" + tag)).asScala.toSet.map({ element: WebElement =>
        element.getText
      })
    }

    catch {
      case e: java.lang.IllegalStateException => Set.empty[String]
    }
  }
}
