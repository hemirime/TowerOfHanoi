package com.github.hemirime.toh
package core

import cats.Show
import cats.syntax.all._

final case class Move(from: Int, to: Int)

object Move:
  given Show[Move] = Show.show(m => s"${m.from + 1} -> ${m.to + 1}")

  def parse(s: String): Option[Move] =
    def parseIndex(c: Char): Option[Int] = Option
      .when(c.isDigit)(c.asDigit - 1)
      .filter(d => d >= 0 && d < 3)

    s.toCharArray match
      case Array(f, ' ', t) =>
        (parseIndex(f), parseIndex(t)).mapN(Move.apply)
      case _ => None
