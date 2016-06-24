package org.scrawler.db

import org.scrawler.website.WebPage

abstract class DatabaseConnection(uri: String, name: String) {
    def saveWebPageToDatabase(webPage: WebPage): Unit
}
