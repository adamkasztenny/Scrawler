package org.scrawler.configuration

import org.scalatest.{FunSuite, Matchers}
import java.io.File

class ConfigurationReaderTest extends FunSuite with Matchers {

  val configurationFilePath = new File("src/test/resources/test-config.json").getAbsolutePath

  test("reads the seed values") {
    ConfigurationReader(configurationFilePath).seed shouldBe Set("http://example.com", "http://example.net")
  }

  test("reads the database configuration") {
    val databaseConfiguration = ConfigurationReader(configurationFilePath).databaseConfiguration
    databaseConfiguration.uri shouldBe "someDb://localhost:4242"
    databaseConfiguration.name shouldBe "someDbName"
    databaseConfiguration.`type` shouldBe "someDb"
  }
}
