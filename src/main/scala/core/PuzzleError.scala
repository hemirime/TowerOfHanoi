package com.github.hemirime.toh
package core

import cats.syntax.all._

enum PuzzleError(msg: String) extends Throwable(msg):
  case NoDiskAvailable(m: Move) extends PuzzleError(s"Rod ${m.from + 1} is empty.")
  case WrongMove(m: Move) extends PuzzleError(show"No disk may be placed on top of a disk that is smaller than it: $m")
  case MoveOutOfBound(m: Move) extends PuzzleError(show"Incorrect move: $m")
  case PuzzleSolved extends PuzzleError("Puzzle already solved")
