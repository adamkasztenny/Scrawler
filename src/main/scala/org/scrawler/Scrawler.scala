package org.scrawler

import org.scrawler.db.DatabaseConnection
import play.api.libs.json.Json
import org.scrawler.queue.Queue
import org.scrawler.db.mongodb.MongoDBDatabaseConnection

object Scrawler {
    var databaseConnection: DatabaseConnection = null

    def main(args: Array[String]): Unit = {
        val rawJsonConfig = scala.io.Source.fromFile(args(0)).mkString

        val jsonConfig = Json.parse(rawJsonConfig)

        val seed: Set[String] = (jsonConfig \ "seed").as[Set[String]]

        val databaseURI = (jsonConfig \ "dburi").as[String]
        val databaseName = (jsonConfig \ "dbname").as[String]
        val databaseType = (jsonConfig \ "dbtype").as[String]

        databaseType match {
            case "mongodb" => databaseConnection = new MongoDBDatabaseConnection (databaseURI, databaseName)
        }

        val queue = new Queue(seed)

        runScrawler(queue)
    }

    def getDatabaseConnection = databaseConnection
    
    private def runScrawler(queue: Queue) = while(!queue.empty) { queue.getFromQueueAndAddLinked }
}
