organization := "adamkasztenny"

version := "1.0.0"

scalaVersion := "2.12.15"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % "2.9.2",

    "org.seleniumhq.webdriver" % "webdriver-selenium" % "0.9.7376",
    "org.seleniumhq.webdriver" % "webdriver-htmlunit" % "0.9.7376",

    "org.mongodb" %% "casbah" % "3.1.1",

    "org.slf4j" % "slf4j-api" % "1.7.32",
    "org.slf4j" % "slf4j-simple" % "1.7.32",

    "org.apache.logging.log4j" % "log4j-core" % "2.15.0"
)

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "org.scalamock" %% "scalamock" % "4.1.0" % Test,
    "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
)
