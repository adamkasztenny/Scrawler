package org.scrawler

import org.scrawler.queue.Queue
import org.scrawler.db.DatabaseFactory
import org.scrawler.db.DatabaseConnection
import org.scrawler.configuration.ConfigurationReader
import scala.annotation.tailrec

object Scrawler {
    private var databaseConnection: DatabaseConnection = null

    def main(args: Array[String]): Unit = {
        val configurationPath = args(0)
        val configuration = ConfigurationReader(configurationPath)

        databaseConnection = DatabaseFactory(configuration.databaseConfiguration) 
        val queue = new Queue(configuration.seed)

        runScrawler(queue)
    }

    def getDatabaseConnection = databaseConnection
    
    @tailrec
    private def runScrawler(queue: Queue): Unit =
      if (queue.empty) return
      else {
        queue.getFromQueueAndAddLinked
        runScrawler(queue)
      }
}
