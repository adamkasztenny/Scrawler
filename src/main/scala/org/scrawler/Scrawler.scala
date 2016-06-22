package org.scrawler

import play.api.libs.json.Json

object Scrawler {
    def main(args: Array[String]): Unit = {        
        val rawJsonConfig = scala.io.Source.fromFile(args(0)).mkString

        val jsonConfig = Json.parse(rawJsonConfig)
       
        val seed: Seq[String] = (jsonConfig \ "seed").as[Seq[String]]
        val databaseURI = (jsonConfig \ "dburi").as[String]
    }
}
