package org.scrawler.queue

import org.scrawler.website.extractor.Extractor
import java.net.URL

class Queue(seed: Set[String]) {
    val websites: collection.mutable.Set[URL] = collection.mutable.Set(seed.map(makeURL(_)))

    def getFromQueueAndAddLinked = {
        Extractor.getLinkedWebsites(websites.head)
    }

    private def makeURL(website: String): URL = {
        if (!website.contains("http")) return new URL("http://" + website)
        new URL(website)
    }
}
