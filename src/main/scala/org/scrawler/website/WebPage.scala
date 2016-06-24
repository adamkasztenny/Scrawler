package org.scrawler.website

import org.scrawler.Scrawler
import java.net.URL

case class WebPage(title: String, url: URL, body: String, keywords: Set[String]) {
    Scrawler.databaseConnection.saveWebPageToDatabase(this)
}
