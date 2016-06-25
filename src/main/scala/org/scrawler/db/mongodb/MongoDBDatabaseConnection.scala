package org.scrawler.db.mongodb

import org.scrawler.db.DatabaseConnection
import org.scrawler.website.WebPage

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.mongodb.casbah.Imports._
import java.util.Calendar 

// thanks to http://mongodb.github.io/casbah/3.1/getting-started/ for all the help!

class MongoDBDatabaseConnection(uri: String, dbName: String) extends DatabaseConnection(uri, dbName) {
    val logger = LoggerFactory.getLogger(this.getClass)

    val mongoClient: MongoClient = MongoClient(uri) 

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
