package org.scrawler.configuration

import org.scrawler.configuration.db.DatabaseConfiguration

case class Configuration (
  seed: Set[String],
  databaseConfiguration: DatabaseConfiguration
)
