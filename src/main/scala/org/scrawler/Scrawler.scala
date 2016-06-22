package org.scrawler

import org.scrawler.json.SeedParser

object Scrawler {
    def main(args: Array[String]): Unit = {
        SeedParser.parseSeed(args(0))
    }
}
