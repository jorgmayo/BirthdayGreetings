package Converters

import CSV.{HeaderReader, RawCSVRow}
import Reader.{CSVValFloat, CSVValInt, CSVValNull, CSVValStr, Header}
import org.scalatest.FunSuite

import scala.annotation.tailrec

class ConverterTest extends FunSuite {
  val path = "src/test/test2.txt"
  val headers: Array[Header] = HeaderReader.fromFileName.extractHeaders(path)

  val expected: List[RawCSVRow] = RawCSVRow(
    Map(
      Header("last_name") -> CSVValStr("Ann"),
      Header("first_name") -> CSVValFloat(3.0),
      Header("date_of_birth") -> CSVValInt(6),
      Header("email") -> CSVValStr("hola")
    )
  ) ::
  RawCSVRow(
    Map(
      Header("last_name") -> CSVValStr("Doe"),
      Header("first_name") -> CSVValFloat(5.0),
      Header("date_of_birth") -> CSVValNull,
      Header("email") -> CSVValInt(3)
    )
  ) :: Nil


  test("fromIterator") {
    val lineIt = Converter.fileNameToIterator.map(path)
    val rawCsVRows: List[RawCSVRow] = collect(lineIt, Nil)
    assert(rawCsVRows sameElements expected)
  }

  @tailrec
  final def collect(it: Iterator[String], acc: List[RawCSVRow]): List[RawCSVRow] = {
    if (!it.hasNext) acc
    else {
      val line = it.next()
      val thisRawCSVRow = Converter.fromHeadersAndLine(headers).map(line)
      collect(it, thisRawCSVRow :: acc)
    }
  }

}
