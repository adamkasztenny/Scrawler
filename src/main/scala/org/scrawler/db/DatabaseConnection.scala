package org.scrawler.db

import org.scrawler.domain.WebPage

abstract class DatabaseConnection(uri: String, name: String) {
    def saveWebPageToDatabase(webPage: WebPage): Unit
}
