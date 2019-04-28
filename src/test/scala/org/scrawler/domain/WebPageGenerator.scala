package org.scrawler.domain

import java.net.URL

import org.scalacheck.Gen
import org.scrawler.Sample._

object WebPageGenerator {
  private def keywords: Set[String] = Seq.fill(5)(sample(Gen.alphaStr)).toSet

  def apply(): WebPage = WebPage(Gen.alphaStr, UrlGenerator(), Gen.alphaNumStr, keywords)
}
