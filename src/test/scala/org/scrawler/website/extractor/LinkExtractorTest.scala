package org.scrawler.website.extractor

import java.net.URL

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalacheck.Gen
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSuite, Matchers}
import org.scrawler.domain.UrlGenerator
import org.scrawler.Sample._
import scala.collection.JavaConverters._

class LinkExtractorTest extends FunSuite with Matchers with MockFactory {

  trait Fixture {
    implicit val driver: WebDriver = mock[WebDriver]
    val url: URL = UrlGenerator()
  }

  trait LinksPresent extends Fixture {
    (driver.get(_: String)) expects url.toString

    val links: Seq[String] = Seq(Gen.alphaStr, Gen.alphaStr).map(link => s"https://${sample(link)}")
    val firstLink = mock[WebElement]
    (firstLink.getAttribute(_: String)) expects "href" returning links.head
    val secondLink = mock[WebElement]
    (secondLink.getAttribute(_: String)) expects "href" returning links.last

    (driver.findElements(_: By)) expects By.xpath("//a") returning List(firstLink, secondLink).asJava
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

  test("should return a set of links on the page") {
    new LinksPresent {
      LinkExtractor(url) should contain theSameElementsAs links.map(new URL(_))
    }
  }

  test("should return an empty set if the web page cannot be retreived") {
    new Fixture {
      (driver.get(_: String)) expects url.toString throwing new RuntimeException()
      LinkExtractor(url) shouldBe empty
    }
  }
}
