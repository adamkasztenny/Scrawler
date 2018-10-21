package org.scrawler

import org.scrawler.queue.Queue
import org.scrawler.db.DatabaseFactory
import org.scrawler.db.DatabaseConnection
import org.scrawler.configuration.ConfigurationReader

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
    
    private def runScrawler(queue: Queue) = while(!queue.empty) { queue.getFromQueueAndAddLinked }
}
