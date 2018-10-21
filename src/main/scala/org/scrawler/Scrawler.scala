package org.scrawler

import org.scrawler.db.DatabaseConnection
import org.scrawler.queue.Queue
import org.scrawler.db.mongodb.MongoDBDatabaseConnection
import org.scrawler.configuration.ConfigurationReader

object Scrawler {
    var databaseConnection: DatabaseConnection = null

    def main(args: Array[String]): Unit = {
        val configurationPath = args(0)
        val configuration = ConfigurationReader(configurationPath)

        configuration.databaseConfiguration.`type` match {
            case "mongodb" => databaseConnection = MongoDBDatabaseConnection(configuration.databaseConfiguration.uri, configuration.databaseConfiguration.name)
            case _ => throw new java.lang.UnsupportedOperationException(s"${configuration.databaseConfiguration.`type`} is not supported")
        }

        val queue = new Queue(configuration.seed)

        runScrawler(queue)
    }

    def getDatabaseConnection = databaseConnection
    
    private def runScrawler(queue: Queue) = while(!queue.empty) { queue.getFromQueueAndAddLinked }
}
