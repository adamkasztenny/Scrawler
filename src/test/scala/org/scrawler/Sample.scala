package org.scrawler

import org.scalacheck.Gen

object Sample {
  implicit def sample[T](gen: Gen[T]): T = gen.sample.get
}
