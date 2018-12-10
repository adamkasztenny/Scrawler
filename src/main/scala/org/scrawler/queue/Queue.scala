package org.scrawler.queue

import org.scrawler.website.extractor.LinkExtractor
import java.net.URL
import org.openqa.selenium.htmlunit.HtmlUnitDriver

class Queue(seed: Set[String])(implicit driver: HtmlUnitDriver) {
    private val websites: collection.mutable.Set[URL] = collection.mutable.Set()
    private val seen: collection.mutable.Set[URL] = collection.mutable.Set()

    websites ++= seed.map(makeURL)

    def dequeue: URL = {
        val current = websites.head
        if (!seen.contains(current)) {
            websites ++= LinkExtractor(current)
            seen += current
        }
        websites -= current
        current
    }

    def empty = websites.isEmpty
    
    private def makeURL(website: String): URL = {
        if (!website.contains("http")) return new URL("http://" + website)
        new URL(website)
    }
}
