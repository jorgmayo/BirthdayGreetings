package CSV.Birthday

import CSV.Raw.{CSVVal, HeaderTyped}
import org.scalatest.FunSuite

class BirthdayCSVRowTest extends FunSuite {

  val headers = HeaderTyped("first_name", CSVFName.refType) :: HeaderTyped("email", CSVEmail.refType) :: Nil
  val goodValues = CSVFName("Jorge") :: CSVEmail("jorge.mayoral@devo.com") :: Nil
  val badValues = CSVFName("Jorge") :: CSVDate("hola") :: Nil

  val goodRow: Map[HeaderTyped, CSVVal] = headers.zip(goodValues).toMap
  val badRow = headers.zip(badValues).toMap

  test("should work building the row") {
    BirthdayCSVRow.withRow(goodRow)
    assertThrows[RuntimeException](BirthdayCSVRow.withRow(badRow))
  }



}
