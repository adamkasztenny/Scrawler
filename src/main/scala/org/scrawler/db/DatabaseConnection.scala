package org.scrawler.db

import org.scrawler.website.WebPage

abstract class DatabaseConnection(uri: String) {
    def saveWebPageToDatabase(webPage: WebPage): Unit
}
