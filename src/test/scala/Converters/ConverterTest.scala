package Converters

import CSV.Birthday.CSVEmail
import CSV.Raw.{CSVValFloat, CSVValInt, CSVValNull, CSVValStr, Header, HeaderTyped, RawCSVRow}
import Reader.HeaderReader
import org.scalatest.FunSuite

import scala.annotation.tailrec

class ConverterTest extends FunSuite {
  val path = "src/test/test2.txt"
  val lList:  LazyList[String] = Converter.fileNameToLazyList.map(path)
  val headers: Array[Header] = HeaderReader.fromLazyList.extractHeaders(lList)

  val expected: List[RawCSVRow] = RawCSVRow(
  Map(
  Header("last_name") -> CSVValStr("Doe"),
  Header("first_name") -> CSVValFloat(5.0),
  Header("date_of_birth") -> CSVValNull,
  Header("email") -> CSVValInt(3)
  )
  ) ::
  RawCSVRow(
  Map(
  Header("last_name") -> CSVValStr("Ann"),
  Header("first_name") -> CSVValFloat(3.0),
  Header("date_of_birth") -> CSVValInt(6),
  Header("email") -> CSVValStr("hola")
  )
  ) :: Nil


  test("fromLList") {
    val rawCsVRows: List[RawCSVRow] = collect(lList, Nil)
    assert(rawCsVRows sameElements expected)
  }

  test("fromStrItToRawCSVRowIT") {
    val csvList = Converter.fromLazyListToRawCSVRowLazyList(headers).map(lList.drop(1))
    assert(csvList sameElements expected)
  }

  test("from_CSVVal_To_TypedCSVVAlTest") {
    val email = CSVValStr("jorge.mayoral@devo.com")
    val header = HeaderTyped("email", CSVEmail.refType)
    val tuple = (header, email)
    val result = Converter.from_CSVVal_To_TypedCSVVal.map(tuple)
    assert(result.equals(CSVEmail(email.x)))
  }

  final def collect(lList: LazyList[String], acc: List[RawCSVRow]): List[RawCSVRow] = {
    lList.drop(1).map(Converter.fromHeadersAndLine(headers).map).toList
  }

}
