package org.scrawler.domain

import java.net.URL

import org.scalacheck.Gen
import org.scrawler.Sample._

object UrlGenerator {
  def apply(containing: String = "") = new URL(s"https://${sample(Gen.alphaStr)}$containing${sample(Gen.alphaStr)}")
}
