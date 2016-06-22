package org.scrawler.json

import play.api.libs.json.Json

object SeedParser {
    def parseSeed(seedJsonFile: String) = Json.parse(seedJsonFile)
}
