package org.scrawler.domain

import java.net.URL

import org.scalacheck.Gen

object WebPageGenerator {
  implicit private def sample[T](gen: Gen[T]): T = gen.sample.get
  private def keywords: Set[String] = Seq.fill(5)(sample(Gen.alphaStr)).toSet
  private def url: String = Gen.alphaStr

  def apply(): WebPage = WebPage(Gen.alphaStr, new URL(s"http://$url"), Gen.alphaNumStr, keywords)
}
