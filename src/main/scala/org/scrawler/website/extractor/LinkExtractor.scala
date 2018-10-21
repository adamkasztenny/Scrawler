package org.scrawler.website.extractor

import java.net.URL
import collection.JavaConverters._

// thanks Alvin: http://alvinalexander.com/scala/how-to-use-java-style-logging-slf4j-scala
import org.slf4j.Logger
import org.slf4j.LoggerFactory

// big thanks to https://joshrendek.com/2013/10/parsing-html-in-scala/ for the HTML parsing stuff
import org.openqa.selenium.htmlunit._
import org.openqa.selenium.WebElement

object LinkExtractor {
    private val logger = LoggerFactory.getLogger(this.getClass)

    private val driver = new HtmlUnitDriver
    
    def apply(website: URL): Set[URL] = {
        if (isObviouslyNotHTML(website)) return Set.empty
        
        try {
            driver.get(website.toString)
        }

        catch {
            case e: java.lang.Exception => return Set.empty
        }
        
        logger.info("Currently scrawling " + website)

        WebPageExtractor(driver, website)

        try {
            driver.findElementsByXPath("//a").asScala.toSet.map({element: WebElement =>
                try {
                     new URL(element.getAttribute("href"))
                }

                catch {
                    case e: java.net.MalformedURLException => return Set.empty
                }
            })
        }

        catch {
            case e: java.lang.IllegalStateException => return Set.empty
        }
    }

    private def isObviouslyNotHTML(website: URL): Boolean = {
        val websiteString = website.toString
        websiteString.contains("mailto") ||
        websiteString.contains(".png")   ||
        websiteString.contains(".jpeg")  ||
        websiteString.contains(".pdf")   ||
        websiteString.contains(".txt") 
    } 
}
