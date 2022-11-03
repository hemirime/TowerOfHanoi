package com.github.hemirime.toh
package core

import core.PuzzleStatus.{InProgress, Solved}
import math.Ordered.orderingToOrdered

import cats.syntax.all.*

final case class Puzzle(board: Board, status: PuzzleStatus, moves: List[Move]):

  def move(move: Move): Either[PuzzleError, Puzzle] =
    status match
      case Solved => PuzzleError.PuzzleSolved.asLeft
      case InProgress =>
        move match
          case m if board.top(m.from).isEmpty =>
            PuzzleError.NoDiskAvailable(m).asLeft
          case m if board.top(m.to).isDefined && board.top(m.from) > board.top(m.to) =>
            PuzzleError.WrongMove(m).asLeft
          case m =>
            board
              .top(m.from)
              .map { d =>
                val b = board.takeOff(m.from).place(d, m.to)
                copy(
                  board = b,
                  status = if b.rods(2).length == b.diskCount then Solved else InProgress,
                  moves = moves :+ m
                )
              }
              .toRight(PuzzleError.MoveOutOfBound(m))

object Puzzle:
  def create(disk: Int = 4): Puzzle =
    Puzzle(Board.create(disk), PuzzleStatus.InProgress, Nil)
