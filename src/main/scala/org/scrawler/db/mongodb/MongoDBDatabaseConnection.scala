package org.scrawler.db.mongodb

import java.util.Calendar

import com.mongodb.casbah.Imports._
import org.scrawler.db.DatabaseConnection
import org.scrawler.website.WebPage
import org.slf4j.LoggerFactory

// thanks to http://mongodb.github.io/casbah/3.1/getting-started/ for all the help!

object MongoDBDatabaseConnection(uri: String, dbName: String) extends DatabaseConnection(uri, dbName) {
    val logger = LoggerFactory.getLogger(this.getClass)

    // thanks http://stackoverflow.com/questions/32322166/mongoexception-on-url-format-when-attempting-connection-to-mongolab#34723804
    val mongoClientURI = new MongoClientURI(uri)
    val mongoClient: MongoClient = MongoClient(mongoClientURI) 

    def saveWebPageToDatabase(webPage: WebPage): Unit = {
        val db = mongoClient(dbName)
        val collection = db("scrawler1")

        val existingURLQuery = MongoDBObject("url" -> webPage.url.toString)

        val update = $set(
            "title" -> webPage.title,
            "url" -> webPage.url.toString,
            "date" -> Calendar.getInstance.getTime,
            "keywords" -> webPage.keywords,
            "body" -> webPage.body
        )

        collection.update(existingURLQuery, update, upsert = true)
        logger.info("Saved " + webPage.url + " to db")
    }
}
