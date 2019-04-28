package org.scrawler.website.extractor

import org.openqa.selenium.WebDriver
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSuite, Matchers}
import org.scrawler.domain.UrlGenerator

class LinkExtractorTest extends FunSuite with Matchers with MockFactory {

  trait Fixture {
    implicit val driver: WebDriver = mock[WebDriver]
  }

  test("should return empty set for email links") {
    new Fixture {
      LinkExtractor(UrlGenerator("mailto")) shouldBe empty
    }
  }

  test("should return empty set for pictures") {
    new Fixture {
      LinkExtractor(UrlGenerator(".png")) shouldBe empty
      LinkExtractor(UrlGenerator(".jpeg")) shouldBe empty
    }
  }

  test("should return empty set for PDFs") {
    new Fixture {
      LinkExtractor(UrlGenerator(".pdf")) shouldBe empty
    }
  }

  test("should return empty set for text files") {
    new Fixture {
      LinkExtractor(UrlGenerator(".txt")) shouldBe empty
    }
  }
}
