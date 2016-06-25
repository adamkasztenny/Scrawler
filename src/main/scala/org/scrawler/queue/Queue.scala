package org.scrawler.queue

import org.scrawler.website.extractor.LinkExtractor
import java.net.URL

class Queue(seed: Set[String]) {
    private val websites: collection.mutable.Set[URL] = collection.mutable.Set()
    private val seen: collection.mutable.Set[URL] = collection.mutable.Set()

    websites ++= seed.map(makeURL(_))

    def getFromQueueAndAddLinked = {
        val current = websites.head
        if (!seen.contains(current)) {
            websites ++= LinkExtractor.getLinkedWebsites(current)
            seen += current
        }
        websites -= current
    }

    def empty = websites.isEmpty
    
    private def makeURL(website: String): URL = {
        if (!website.contains("http")) return new URL("http://" + website)
        new URL(website)
    }
}
