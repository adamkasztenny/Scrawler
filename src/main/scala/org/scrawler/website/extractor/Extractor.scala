package org.scrawler.website.extractor

import java.net.URL
import collection.JavaConverters._

// big thanks to https://joshrendek.com/2013/10/parsing-html-in-scala/ for the HTML parsing stuff
import org.openqa.selenium.htmlunit._
import org.openqa.selenium.WebElement

object Extractor {
    val driver = new HtmlUnitDriver
    
    def getLinkedWebsites(website: URL): Set[URL] = {
        println(website.toString)
        val html = driver.get(website.toString)
        val links = driver.findElementsByXPath("//a").asScala.toSet.map({element: WebElement => 
            new URL(element.getAttribute("href"))
        })
        links
    }
}
