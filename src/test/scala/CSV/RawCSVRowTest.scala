package CSV

import CSV.Raw.{Header, RawCSVRow}
import Converters.Converter
import Reader.{HeaderReader, LineReader}
import org.scalatest.FunSuite

class RawCSVRowTest extends FunSuite {
  val lines =
    """last_name,first_name,date_of_birth,email
       |Doe, 5.0,,3
       |Ann, 3.0,6,hola
       """.stripMargin
  val headers: Array[Header] = HeaderReader.fromFirstLine.extractHeaders(lines)

  test("genMap") {
    val linesRead = LineReader.fromStr.extractLines(lines)
    val extracted: RawCSVRow = Converter.fromHeadersAndLine(headers).map(linesRead.head)
    println(extracted)
  }
}
