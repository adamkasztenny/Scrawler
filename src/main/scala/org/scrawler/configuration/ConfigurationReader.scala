package org.scrawler.configuration

import org.scrawler.configuration.db.DatabaseConfiguration
import play.api.libs.json.Json

import scala.io.Source

object ConfigurationReader {

  def apply(path: String): Configuration = {
    val rawJsonConfig = Source.fromFile(path).mkString

    val jsonConfig = Json.parse(rawJsonConfig)

    val seed: Set[String] = (jsonConfig \ "seed").as[Set[String]]

    val uri = (jsonConfig \ "database" \ "uri").as[String]
    val name = (jsonConfig \ "database" \ "name").as[String]
    val `type` = (jsonConfig \ "database" \ "type").as[String]

    Configuration(seed = seed, DatabaseConfiguration(uri = uri, name = name, `type` = `type`))
  }
}
