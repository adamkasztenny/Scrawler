package org.scrawler.domain

import java.net.URL

import org.scalacheck.Gen
import org.scrawler.Sample._

object WebPageGenerator {
  private def keywords: Set[String] = Seq.fill(5)(sample(Gen.alphaStr)).toSet

  private def url: String = Gen.alphaStr

  def apply(): WebPage = WebPage(Gen.alphaStr, new URL(s"http://$url"), Gen.alphaNumStr, keywords)
}
