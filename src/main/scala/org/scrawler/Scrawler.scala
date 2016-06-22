package org.scrawler

import play.api.libs.json.Json
import org.scrawler.queue.Queue

object Scrawler {
    def main(args: Array[String]): Unit = {        
        val rawJsonConfig = scala.io.Source.fromFile(args(0)).mkString

        val jsonConfig = Json.parse(rawJsonConfig)
       
        val seed: Set[String] = (jsonConfig \ "seed").as[Set[String]]
        val databaseURI = (jsonConfig \ "dburi").as[String]

        val queue = new Queue(seed)
    }
}
