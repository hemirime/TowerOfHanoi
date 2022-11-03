package com.github.hemirime.toh

import core.*
import core.PuzzleStatus.{InProgress, Solved}

import cats.effect.IO
import cats.syntax.all.*

import scala.util.{Failure, Success, Try}

final class PuzzleRunner(console: Console):

  private def readMove(): IO[Move] = for
    _ <- console.printLine("Enter numbers of start and finish rods to move disk:")
    str <- console.readLine
    move <- Move.parse(str) match
      case Some(m) => IO.pure(m)
      case None    => console.printLine("Please enter correct start and end numbers (from 1 to 3)") >> readMove()
  yield move

  private def mainLoop(puzzle: Puzzle): IO[Unit] = for
    _ <- console.printLine(show"\n${puzzle.board}\n")
    _ <- puzzle.status match
      case Solved =>
        console.printLine(s"Puzzle solved in ${puzzle.moves.length} moves:")
          >> console.printLine(puzzle.moves.map(_.show).mkString(", "))
      case InProgress =>
        for
          move <- readMove()
          _ <- puzzle.move(move) match
            case Left(err) => console.printLine(err.getMessage) >> mainLoop(puzzle)
            case Right(p)  => mainLoop(p)
        yield ()
  yield ()

  private def readDiskCount(): IO[Int] = for
    _ <- console.printLine("Enter number of disks:")
    str <- console.readLine
    disk <- Try(str.toInt).filter(_ > 0) match
      case Failure(_)      => console.printLine("Please enter valid number > 0") >> readDiskCount()
      case Success(number) => IO.pure(number)
  yield disk

  def run: IO[Unit] = for
    _ <- console.printLine("Tower of Hanoi")
    disk <- readDiskCount()
    _ <- mainLoop(Puzzle.create(disk))
  yield ()
