package com.github.hemirime.toh

import core.*

import cats.effect.{IO, IOApp}
import cats.syntax.all.*

object Main extends IOApp.Simple:
  override def run: IO[Unit] = PuzzleRunner(StdConsole).run
