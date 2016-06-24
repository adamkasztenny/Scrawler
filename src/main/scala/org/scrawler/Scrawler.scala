package org.scrawler

import play.api.libs.json.Json

import org.scrawler.queue.Queue
import org.scrawler.db.mongodb.MongoDBDatabaseConnection

object Scrawler {
    var mongoDBDatabaseConnection: MongoDBDatabaseConnection = null

    def main(args: Array[String]): Unit = {
        val rawJsonConfig = scala.io.Source.fromFile(args(0)).mkString

        val jsonConfig = Json.parse(rawJsonConfig)
       
        val seed: Set[String] = (jsonConfig \ "seed").as[Set[String]]

        val databaseURI = (jsonConfig \ "dburi").as[String]
        val databaseName = (jsonConfig \ "dbname").as[String]

        mongoDBDatabaseConnection = new MongoDBDatabaseConnection(databaseURI, databaseName)

        val queue = new Queue(seed)

        runScrawler(queue)
    }

    def databaseConnection = mongoDBDatabaseConnection
    
    private def runScrawler(queue: Queue) = while(!queue.empty) { queue.getFromQueueAndAddLinked }
}
