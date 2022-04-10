package Converters

import CSV.HeaderReader
import Reader.Header
import org.scalatest.FunSuite

class HeaderReaderTest extends FunSuite {

  test("fromFirstLineTest") {
    val line1 = "first_name, last_name, day, email\n holii"
    val expected = Array(Header("first_name"), Header("last_name"), Header("day"), Header("email"))
    val headers1 = HeaderReader.fromFirstLine.extractHeaders(line1)
    assert(headers1 sameElements expected)
  }
}
