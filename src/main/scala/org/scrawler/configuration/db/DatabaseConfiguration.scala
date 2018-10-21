package org.scrawler.configuration.db

case class DatabaseConfiguration(
  name: String,
  uri: String,
  `type`: String
)
