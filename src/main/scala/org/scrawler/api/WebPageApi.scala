package org.scrawler.api

import org.scrawler.db.DatabaseConnection
import org.scrawler.domain.WebPage

class WebPageApi(databaseConnection: DatabaseConnection) {

  def saveWebPage(webPage: WebPage): Unit = databaseConnection.saveWebPage(webPage)
}
