package org.scrawler

import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.scrawler.api.WebPageApi
import org.scrawler.configuration.ConfigurationReader
import org.scrawler.db.DatabaseFactory
import org.scrawler.queue.Queue
import org.scrawler.website.extractor.WebPageExtractor

import scala.annotation.tailrec

object Scrawler extends App {

  override def main(args: Array[String]): Unit = {
    implicit val driver: HtmlUnitDriver = new HtmlUnitDriver()
    val configurationPath = args.head
    val configuration = ConfigurationReader(configurationPath)

    val databaseConnection = DatabaseFactory(configuration.databaseConfiguration)
    val webPageApi = new WebPageApi(databaseConnection)
    val queue = new Queue(configuration.seed)

    runScrawler(queue, webPageApi)
  }

  @tailrec
  private def runScrawler(queue: Queue,
                          webPageApi: WebPageApi)(implicit driver: HtmlUnitDriver): Unit =
    if (queue.empty) return
    else {
      val currentUrl = queue.dequeue
      val webPage = WebPageExtractor(currentUrl)
      webPageApi.saveWebPage(webPage)
      runScrawler(queue, webPageApi)
    }
}
