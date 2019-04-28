package org.scrawler.website.extractor

import java.net.URL

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalacheck.Gen
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import org.scrawler.Sample._
import org.scrawler.domain.{UrlGenerator, WebPage}

import scala.collection.JavaConverters._

class WebPageExtractorTest extends FlatSpec with Matchers with MockFactory {

  trait Fixture {
    implicit val driver: WebDriver = mock[WebDriver]
    val url: URL = UrlGenerator()
  }

  trait AllElementsPresent extends Fixture {
    val title: String = words
    (driver.getTitle _) expects() returning title

    val body: String = words
    (driver.findElements(_: By)) expects By.xpath("//body") returning webElements(body)

    val bold: String = words
    (driver.findElements(_: By)) expects By.xpath("//b") returning webElements(bold)
    val strong: String = words
    (driver.findElements(_: By)) expects By.xpath("//strong") returning webElements(strong)

    val headers: Seq[String] = Seq.fill(6)(words)
    (1 to 6).foreach(index =>
      (driver.findElements(_: By)) expects By.xpath(s"//h$index") returning webElements(headers(index - 1)))

    val webPage: WebPage = WebPageExtractor(url)
  }

  trait MissingElements extends Fixture {
    (driver.getTitle _) expects() returning null
    (driver.findElements(_: By)) expects By.xpath("//body") throwing new IllegalStateException()
    (driver.findElements(_: By)) expects By.xpath("//b") throwing new IllegalStateException()
    (driver.findElements(_: By)) expects By.xpath("//strong") throwing new IllegalStateException()
    (1 to 6).foreach(index =>
      (driver.findElements(_: By)) expects By.xpath(s"//h$index") throwing new IllegalStateException())

    val webPage: WebPage = WebPageExtractor(url)
  }

  "extracting a web page" should "include a title, if the page has one" in new AllElementsPresent {
    webPage.title shouldBe title
  }

  it should "include a blank title, if the page has none" in new MissingElements {
    webPage.title shouldBe ""
  }

  it should "include the URL" in new AllElementsPresent {
    webPage.url shouldBe url
  }

  it should "include the body, if there is one" in new AllElementsPresent {
    webPage.body shouldBe body
  }

  it should "include a blank body, if the page has none" in new MissingElements {
    webPage.body shouldBe ""
  }

  it should "include bold and strong text in the keywords" in new AllElementsPresent {
    webPage.keywords should contain allElementsOf asKeywords(bold) ++ asKeywords(strong)
  }

  it should "include header text in the keywords" in new AllElementsPresent {
    webPage.keywords should contain allElementsOf headers.flatMap(asKeywords)
  }

  it should "include the title in the keywords" in new AllElementsPresent {
    webPage.keywords should contain allElementsOf asKeywords(title)
  }

  it should "propagate a non-IllegalStateException if one happens" in new Fixture {
    (driver.getTitle _) expects() returning null
    val exception = new RuntimeException(Gen.alphaStr)
    (driver.findElements(_: By)) expects * throwing exception

    val thrown = intercept[RuntimeException] {
      WebPageExtractor(url)
    }

    thrown shouldBe exception
  }

  "keywords" should "not include spaces" in new AllElementsPresent {
    webPage.keywords.find(_.contains(" ")) shouldBe empty
  }

  they should "not include dots" in new AllElementsPresent {
    webPage.keywords.find(_.contains(".")) shouldBe empty
  }

  they should "not include commas" in new AllElementsPresent {
    webPage.keywords.find(_.contains(",")) shouldBe empty
  }

  they should "be empty if the page is missing elements and a title" in new MissingElements {
    webPage.keywords should contain only ""
  }

  private def webElements(text: String): java.util.List[WebElement] = {
    val webElement = mock[WebElement]
    (webElement.getText _) expects() returning text
    List(webElement).asJava
  }

  private def asKeywords(string: String): Seq[String] = string.toLowerCase.replace(".", "").replace(",", "").split(" ")

  private def words: String = s"${sample(Gen.asciiStr)} ${sample(Gen.asciiStr)}"
}
