package com.github.hemirime.toh
package core

import munit.FunSuite
import cats.syntax.all._

class PuzzleSpec extends FunSuite {

  extension (p: Puzzle)
    def moveMultiple(strs: String*) =
      strs
        .flatMap(Move.parse)
        .foldLeft(p.asRight[PuzzleError])((r, m) =>
          r match
            case l @ Left(_) => l
            case Right(p)    => p.move(m)
        )

  test("should fail if no disk can be moved") {
    val sut = Puzzle.create()
    val move = Move.parse("2 3").get

    val Left(err) = sut.move(move): @unchecked
    assertEquals(err, PuzzleError.NoDiskAvailable(move))
  }

  test("should fail when larger disk moved on smaller") {
    val sut = Puzzle.create()
    val move = Move.parse("1 3").get

    val Left(err) = sut.moveMultiple("1 3", "1 3"): @unchecked
    assertEquals(err, PuzzleError.WrongMove(move))
  }

  test("should fail when puzzle already solved") {
    val sut = Puzzle.create(1)

    val Left(err) = sut.moveMultiple("1 3", "1 3"): @unchecked
    assertEquals(err, PuzzleError.PuzzleSolved)
  }

  test("should solved when all disk moved to last rod") {
    val sut = Puzzle.create(2)

    val Right(p) = sut.moveMultiple("1 2", "1 3", "2 3"): @unchecked
    assertEquals(p.status, PuzzleStatus.Solved)
  }

}
