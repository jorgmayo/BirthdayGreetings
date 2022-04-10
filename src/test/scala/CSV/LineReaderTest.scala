package CSV

import org.scalatest.FunSuite

class LineReaderTest extends FunSuite {
  val lines =
    """last_name,first_name,date_of_birth,email
       |Doe, 5.0,,3
       |Ann, 3.0,6,hola
       |""".stripMargin

  test("readLines") {
    println(LineReader.fromStr.extractLines(lines))
  }
}
