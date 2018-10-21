package org.scrawler.website.extractor

import collection.JavaConverters._

import org.scrawler.website.WebPage

import org.openqa.selenium.htmlunit._
import org.openqa.selenium.WebElement
import java.net.URL

object WebPageExtractor {
   def apply(url: URL)(implicit driver: HtmlUnitDriver): WebPage = {
        val title = Option(driver.getTitle).getOrElse("")

        val body = {
            try {
                driver.findElementByXPath("//body").getText
            }
       
            catch {
                case e: java.lang.IllegalStateException => ""
            }     
        }

        val bold = elements("b") ++ elements("strong")
        val keywords = bold ++ headers ++ title.split(" ").toSet
        val sanitizedKeywords = keywords.flatMap(_.split(" ").toSet).map(_.toLowerCase).map({
          keyword: String => keyword.replace(",", "").replace(".", "")
        })

        new WebPage(title, url, body, sanitizedKeywords)
   }

    private def headers(implicit driver: HtmlUnitDriver): Set[String] = (1 to 6).flatMap(i => elements(s"h$i")).toSet

    private def elements(tag: String)(implicit driver: HtmlUnitDriver) = {
        try { 
            driver.findElementsByXPath("//" + tag).asScala.toSet.map({element: WebElement =>
                element.getText
            })
        }

        catch {
            case e: java.lang.IllegalStateException => Set("") 
        }
    }
}
