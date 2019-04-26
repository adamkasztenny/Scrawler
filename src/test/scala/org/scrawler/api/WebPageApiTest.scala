package org.scrawler.api

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSuite, Matchers}
import org.scrawler.db.DatabaseConnection
import org.scrawler.domain.{WebPage, WebPageGenerator}

class WebPageApiTest extends FunSuite with Matchers with MockFactory {

  test("uses the database connection to save a Web Page") {
    val webPage = WebPageGenerator()

    val databaseConnection = mock[DatabaseConnection]
    (databaseConnection.saveWebPage(_: WebPage)) expects webPage

    new WebPageApi(databaseConnection).saveWebPage(webPage)
  }
}
