organization := "slideon"

version := "0.1.1"

scalaVersion := "2.12.4"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "com.typesafe.play" %% "play-json" % "2.6.8",

    "org.seleniumhq.webdriver" % "webdriver-selenium" % "0.9.7376",
    "org.seleniumhq.webdriver" % "webdriver-htmlunit" % "0.9.7376",

    "org.mongodb" %% "casbah" % "3.1.1",

    "org.slf4j" % "slf4j-api" % "1.7.5",
    "org.slf4j" % "slf4j-simple" % "1.7.5",

    "log4j" %  "log4j" % "1.2.17"
)
