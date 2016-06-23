lazy val commonSettings = Seq(
  organization := "slideon",
  version := "0.1.0",
  scalaVersion := "2.11.6"
)

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq("com.typesafe.play" %% "play-json" % "2.3.4",
    "org.seleniumhq.webdriver" % "webdriver-selenium" % "0.9.7376",
    "org.seleniumhq.webdriver" % "webdriver-htmlunit" % "0.9.7376"
)
