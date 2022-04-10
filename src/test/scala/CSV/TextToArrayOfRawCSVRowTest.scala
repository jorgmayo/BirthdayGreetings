package CSV

import Converters.Converter
import org.scalatest.FunSuite

class TextToArrayOfRawCSVRowTest extends FunSuite {
  val lines =
    """last_name,first_name,date_of_birth,email
      |Doe, 5.0,,3
      |Ann, 3.0,6,hola
      | """.stripMargin
  test("TextToArrayOfRawCSVRowTest") {
    //println(Converter.textToArrayOfRawCSVRow.map(lines).mkString("Array(", ", ", ")"))
  }


}
