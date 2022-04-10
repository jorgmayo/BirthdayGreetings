package CSV

import Reader.{CSVValFloat, CSVValInt, CSVValNull, CSVValStr}
import org.scalatest.FunSuite

class SimpleParserTest extends FunSuite {

  val values: List[String] = ("3" :: "" :: "5.0" :: "holaa" :: Nil)
  val line = "3, \"\", 5.0, holaa"

  test("fromVal") {
    val output = values.map(s => SimpleParser.toCSVVal.parseVal(s))
    assert(CSVValInt(3) == output.head)
    assert(CSVValNull == output(1))
    assert(CSVValFloat(5.0) == output(2))
    assert(CSVValStr("holaa") == output(3))
  }

  test("separateFromLine") {
    val output = SimpleParser.toCSVVal.separate(line)
    assert(output == values)
  }

  test("fromLine") {
    val output = SimpleParser.toCSVVal.parseLine(line)
    assert(output ==
      CSVValInt(3) :: CSVValNull :: CSVValFloat(5.0) :: CSVValStr("holaa") :: Nil)
  }

}
