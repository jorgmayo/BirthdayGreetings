package Converters

import CSV.Birthday.BirthdayCSVRow
import CSV.Raw.Header
import Reader.HeaderReader
import org.scalatest.FunSuite

class FromFileNameToBirthdayCSVRowsTest extends FunSuite {
  val path = "/home/jorgemayoral/Cursos/KataDojo/BirthdayGreetings/src/test/simplyType.txt"
  private val csvRows: LazyList[BirthdayCSVRow] =
    FromFileNameToBirthdayCSVRows.map(path)
  csvRows.foreach(println)
}