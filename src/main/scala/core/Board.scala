package com.github.hemirime.toh
package core

import scala.collection.immutable.ArraySeq
import cats.Show
import cats.syntax.all._

final case class Board(rods: ArraySeq[List[Disk]], diskCount: Int):
  def top(rod: Int): Option[Disk] =
    rods(rod).headOption

  def takeOff(rod: Int): Board =
    this.copy(rods = rods.updated(rod, rods(rod).tail))

  def place(d: Disk, rod: Int): Board =
    this.copy(rods = rods.updated(rod, d :: rods(rod)))

object Board:
  def create(disks: Int): Board =
    Board(ArraySeq(List.tabulate(disks)(d => Disk(d + 1)), Nil, Nil), disks)

  given Show[Disk] = Show.show(d => "█" * (d.size * 2 - 1))

  given Show[Board] with
    override def show(b: Board): String =
      val columnWidth = b.diskCount * 2 - 1

      def row(str: String): String =
        val spaces = (columnWidth - str.length) / 2
        val pad = " " * spaces
        pad ++ str ++ pad

      lazy val emptyRow = row("┆")

      def column(rod: List[Disk], index: Int): List[String] =
        List.fill(b.diskCount - rod.size)(emptyRow)
          ++ rod.map(d => row(d.show))
          :+ row(index.toString)

      val rods = b.rods.indices.map(i => column(b.rods(i), i + 1))
      rods(0)
        .lazyZip(rods(1))
        .lazyZip(rods(2))
        .map((a, b, c) => s"$a $b $c")
        .mkString("\n")
