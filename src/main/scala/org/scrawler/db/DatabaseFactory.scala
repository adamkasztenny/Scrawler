package org.scrawler.db

import org.scrawler.configuration.db.DatabaseConfiguration
import org.scrawler.db.mongodb.MongoDBDatabaseConnection

object DatabaseFactory {

  def apply(configuration: DatabaseConfiguration): DatabaseConnection = {
        configuration.`type` match {
            case "mongodb" => new MongoDBDatabaseConnection(configuration.uri, configuration.name)
            case _ => throw new java.lang.UnsupportedOperationException(s"${configuration.`type`} is not supported")
        }

  }
}
