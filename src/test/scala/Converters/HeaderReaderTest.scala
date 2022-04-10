package Converters

import CSV.HeaderReader
import org.scalatest.FunSuite

class HeaderReaderTest extends FunSuite {

  test("fromFirstLineTest") {
    val line1 = "first_name, last_name, day, email"
    val headers1 = HeaderReader.fromFirstLine.extractHeaders(line1)
    println(headers1)

  }
}
