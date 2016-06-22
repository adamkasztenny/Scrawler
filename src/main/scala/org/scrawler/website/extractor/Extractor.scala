package org.scrawler.website.extractor

import scala.io.Source
import scala.util.matching.Regex
import java.net.URL

object Extractor {
    // thanks to http://stackoverflow.com/questions/7586605/scala-pattern-matching-against-urls
    val URLRegex = """(http|https)://(.*)\.([a-z]+)""".r

    def getLinkedWebsites(website: URL): Set[URL] = {
        // thanks to http://alvinalexander.com/scala/scala-how-to-download-url-contents-to-string-file
        val html = Source.fromURL(website.toString).mkString
        println(html)
        (URLRegex findAllIn html).toSet.map(new URL(_))
    }
}
