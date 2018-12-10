package org.scrawler

import org.scrawler.queue.Queue
import org.scrawler.db.DatabaseFactory
import org.scrawler.db.DatabaseConnection
import org.scrawler.configuration.ConfigurationReader
import org.scrawler.api.WebPageApi
import org.scrawler.website.extractor.WebPageExtractor
import scala.annotation.tailrec
import org.openqa.selenium.htmlunit.HtmlUnitDriver

object Scrawler extends App {
    private var databaseConnection: DatabaseConnection = null
    private val webPageApi: WebPageApi = new WebPageApi(databaseConnection)
    private implicit val driver: HtmlUnitDriver = new HtmlUnitDriver()

    override def main(args: Array[String]): Unit = {
        val configurationPath = args(0)
        val configuration = ConfigurationReader(configurationPath)

        databaseConnection = DatabaseFactory(configuration.databaseConfiguration) 
        val queue = new Queue(configuration.seed)

        runScrawler(queue)
    }
    
    @tailrec
    private def runScrawler(queue: Queue): Unit =
      if (queue.empty) return
      else {
        val currentUrl = queue.dequeue
        val webPage = WebPageExtractor(currentUrl)
        webPageApi.saveWebPage(webPage)

        runScrawler(queue)
      }
}
