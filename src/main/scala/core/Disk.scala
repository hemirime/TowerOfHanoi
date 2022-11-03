package com.github.hemirime.toh
package core

import cats.Show

final case class Disk(size: Int)

object Disk:
  given Ordering[Disk] = Ordering.by(_.size)
  given Show[Disk] = Show.show(d => "█" * d.size)
