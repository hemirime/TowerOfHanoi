package com.github.hemirime.toh
package core

import munit.FunSuite
import cats.syntax.option._

class MoveSpec extends FunSuite {

  test("should parse correct move (from 1 up to 9)") {
    val cases = List(
      "1 3" -> Move(0, 2),
      "1 2" -> Move(0, 1),
      "2 1" -> Move(1, 0),
      "2 3" -> Move(1, 2),
      "3 1" -> Move(2, 0),
      "3 2" -> Move(2, 1)
    )

    cases.foreach { case (str, expected) =>
      assertEquals(Move.parse(str), expected.some, str)
    }
  }

  test("should reject incorrect move") {
    val cases = List(
      "10 1",
      "3 0",
      "0 2",
      "1 4",
      "a 2",
      "c 1",
      "12",
      "-1 2"
    )

    cases.foreach { str =>
      assertEquals(Move.parse(str), none)
    }
  }
}
