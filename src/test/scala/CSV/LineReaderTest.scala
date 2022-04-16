package CSV

import Reader.LineReader
import org.scalatest.FunSuite

class LineReaderTest extends FunSuite {
  val lines =
    """last_name,first_name,date_of_birth,email
       |Doe, 5.0,,3
       |Ann, 3.0,6,hola
       |""".stripMargin

  val expected = Array("Doe, 5.0,,3", "Ann, 3.0,6,hola")
  test("fromStr") {
    assert(LineReader.fromStr.extractLines(lines) sameElements expected)
  }

  test("fromFile") {
    assert(LineReader.fromFileName.extractLines("src/test/test2.txt") sameElements expected)
  }
}
