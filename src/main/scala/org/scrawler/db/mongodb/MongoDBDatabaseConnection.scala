package org.scrawler.db.mongodb

import org.scrawler.db.DatabaseConnection
import org.scrawler.website.WebPage

import com.mongodb.casbah.Imports._
import java.util.Calendar 

// thanks to http://mongodb.github.io/casbah/3.1/getting-started/ for all the help!

class MongoDBDatabaseConnection(uri: String, dbName: String) extends DatabaseConnection(uri) {
    val mongoClient: MongoClient = MongoClient(uri) 

    def saveWebPageToDatabase(webPage: WebPage) = {
        val db = mongoClient(dbName)
        val collection = db("scrawler1")

        collection.insert(
            MongoDBObject(
                "title" -> webPage.title,
                "url" -> webPage.url.toString,
                "date" -> Calendar.getInstance.getTime,
                "keywords" -> webPage.keywords,
                "body" -> webPage.body
            )
        )  
    }
}
