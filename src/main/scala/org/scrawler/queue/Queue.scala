package org.scrawler.queue

class Queue(seed: Seq[String]) {
    val websites: Seq[String] = seed.map(appendHttp(_))

    private def appendHttp(website: String) = {
        var appended = ""

        if (!website.contains("http")) appended += "http://"
        if (!website.contains("www.")) appended += "www."
        appended + website
    }
}
