package org.scrawler.queue

import org.scrawler.website.extractor.Extractor
import java.net.URL

class Queue(seed: Set[String]) {
    val websites: collection.mutable.Set[URL] = collection.mutable.Set()

    websites ++= seed.map(makeURL(_))

    def getFromQueueAndAddLinked = {
        val current = websites.head
        websites ++= Extractor.getLinkedWebsites(current)
        websites -= current
    }

    def retrieveSites = {
        while (true) {
            getFromQueueAndAddLinked
            println(websites)
        }
    }
retrieveSites
    private def makeURL(website: String): URL = {
        if (!website.contains("http")) return new URL("http://" + website)
        new URL(website)
    }
}
