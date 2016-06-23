package org.scrawler.website.extractor

import java.net.URL
import collection.JavaConverters._

// big thanks to https://joshrendek.com/2013/10/parsing-html-in-scala/ for the HTML parsing stuff
import org.openqa.selenium.htmlunit._
import org.openqa.selenium.WebElement

// TODO: rename to LinkExtractor
object Extractor {
    val driver = new HtmlUnitDriver
    
    def getLinkedWebsites(website: URL): Set[URL] = {
        if (website.toString.contains("mailto")) return Set()
        println(website)
        
        val html = driver.get(website.toString)
        // TODO: new WebPageExtractor(html).getWebPage
        val links = driver.findElementsByXPath("//a").asScala.toSet.map({element: WebElement => 
           try {
             new URL(element.getAttribute("href"))
           }
        
           catch {
            case e: java.net.MalformedURLException => new URL(driver.getCurrentUrl + element.getAttribute("href")) 
           }
        })
        links
    }
}
