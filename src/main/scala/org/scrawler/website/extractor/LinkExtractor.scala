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
    val logger = LoggerFactory.getLogger(this.getClass) 

    val driver = new HtmlUnitDriver
    
    def getLinkedWebsites(website: URL): Set[URL] = {
        if (isObviouslyNotHTML(website)) return Set()
        
        try { 
            driver.get(website.toString)
        }

        catch {
            case e: java.lang.Exception => return Set(new URL("http://default.com"))
        }
        
        logger.info("Currently scrawling " + website)

        WebPageExtractor.getWebPage(driver, website)

        val links = {
            try {  
                driver.findElementsByXPath("//a").asScala.toSet.map({element: WebElement => 
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
        links
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