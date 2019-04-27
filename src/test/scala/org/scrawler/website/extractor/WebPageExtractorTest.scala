package org.scrawler.website.extractor

import java.net.URL

import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalacheck.Gen
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import org.scrawler.Sample._
import org.scrawler.domain.WebPage

import scala.collection.JavaConverters._

class WebPageExtractorTest extends FlatSpec with Matchers with MockFactory {

  trait Fixture {
    implicit val driver: WebDriver = mock[WebDriver]

    val url: URL = new URL("https://" + Gen.alphaStr)

    val title: String = words
    (driver.getTitle _) expects() returning title

    val body: String = words
    (driver.findElement(_: By)) expects By.xpath("//body") returning webElement(body)

    val bold: String = words
    (driver.findElements(_: By)) expects By.xpath("//b") returning webElements(bold)
    val strong: String = words
    (driver.findElements(_: By)) expects By.xpath("//strong") returning webElements(strong)

    val headers: Seq[String] = Seq.fill(6)(words)
    (1 to 6).foreach(index =>
      (driver.findElements(_: By)) expects By.xpath(s"//h$index") returning webElements(headers(index - 1)))

    val webPage: WebPage = WebPageExtractor(url)
  }

  "extracting a web page" should "include a title, if the page has one" in new Fixture {
    webPage.title shouldBe title
  }

  it should "include a blank title, if the page has none" in new Fixture {
    override val title = null
    webPage.title shouldBe ""
  }

  it should "include the URL" in new Fixture {
    webPage.url shouldBe url
  }

  it should "include the body" in new Fixture {
    webPage.body shouldBe body
  }

  it should "include bold and strong text in the keywords" in new Fixture {
    webPage.keywords should contain allElementsOf asKeywords(bold) ++ asKeywords(strong)
  }

  it should "include header text in the keywords" in new Fixture {
    webPage.keywords should contain allElementsOf headers.flatMap(asKeywords)
  }

  it should "include the title in the keywords" in new Fixture {
    webPage.keywords should contain allElementsOf asKeywords(title)
  }

  "keywords" should "not include spaces" in new Fixture {
    webPage.keywords.find(_.contains(" ")) shouldBe empty
  }

  they should "not include dots" in new Fixture {
    webPage.keywords.find(_.contains(".")) shouldBe empty
  }

  they should "not include commas" in new Fixture {
    webPage.keywords.find(_.contains(",")) shouldBe empty
  }

  private def webElements(text: String): java.util.List[WebElement] = List(webElement(text)).asJava

  private def webElement(text: String): WebElement = {
    val element = mock[WebElement]
    (element.getText _) expects() returning text
    element
  }

  private def asKeywords(string: String): Seq[String] = string.toLowerCase.replace(".", "").replace(",", "").split(" ")

  private def words: String = s"${sample(Gen.asciiStr)} ${sample(Gen.asciiStr)}"
}
