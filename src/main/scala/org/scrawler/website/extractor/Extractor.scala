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
        // TODO: refactor & improve
        if (website.toString.contains("mailto")) return Set()
        if (website.toString.contains(".png")) return Set()
        if (website.toString.contains(".jpeg")) return Set()
        
        println(website) // TODO: replace with logging statement
        
        driver.get(website.toString)
        WebPageExtractor.getWebPage(driver, website)

        val links = driver.findElementsByXPath("//a").asScala.toSet.map({element: WebElement => 
           try {
             new URL(element.getAttribute("href"))
           }
        
           catch {
            case e: java.net.MalformedURLException => new URL("https://default.com") 
           }
        })
        links
    }
}
