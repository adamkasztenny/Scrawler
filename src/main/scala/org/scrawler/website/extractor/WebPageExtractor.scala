package org.scrawler.website.extractor

import collection.JavaConverters._

import org.scrawler.website.WebPage

import org.openqa.selenium.htmlunit._
import org.openqa.selenium.WebElement
import java.net.URL

object WebPageExtractor {
   def getWebPage(driver: HtmlUnitDriver, url: URL): WebPage = {
        val title = driver.getTitle
    
        val body = {
            try {
                driver.findElementByXPath("//body").getText
            }
       
            catch {
                case e: java.lang.IllegalStateException => "" 
            }     
        }

        val headers1 = getElements(driver, "h1")
        val headers2 = getElements(driver, "h2")
        val bold = getElements(driver, "b") ++ getElements(driver, "strong")

        if (title == null || bold == null || headers1 == null || headers2 == null) {
            return WebPage("Default", new URL("http://default.com"), "non-HTML page found", Set(""))
        }

        val keywords = (bold ++ headers1 ++ headers2 ++ title.split(" ").toSet)
        val sanitizedKeywords = keywords.map(_.split(" ").toSet).flatten.map(_.toLowerCase).map({keyword: String => keyword.replace(",", "").replace(".", "")})        

        new WebPage(title, url, body, sanitizedKeywords)
   }


    private def getElements(driver: HtmlUnitDriver, element: String) = {
        try { 
            driver.findElementsByXPath("//" + element).asScala.toSet.map({element: WebElement => 
                element.getText
            })
        }

        catch {
            case e: java.lang.IllegalStateException => Set("") 
        }
    }
}
