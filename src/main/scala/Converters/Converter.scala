package Converters

import CSV.Birthday.{CSVDate, CSVEmail, CSVFName, CSVLName}
import CSV.Parser.SimpleParser
import CSV.Raw.{CSVVal, CSVValStr, Header, HeaderTyped, RawCSVRow, ValueTyped}
import Reader.{HeaderReader, LineReader}

import scala.io.Source
import scala.util.Using

/**
 * Transforma un tipo A en un tipo B
 */
trait Converter[A, B] {
  def map(x: A): B = throw new NotImplementedError("not implemented")
}

object Converter {
  def fromLazyListToRawCSVRowLazyList(headers: Array[Header]) = new Converter[LazyList[String], LazyList[RawCSVRow]] {
    override def map(x: LazyList[String]): LazyList[RawCSVRow] = {
      fromStrLazyListToRawCSVRowIT(headers).map(x)
    }
  }


  //FIXME: Extract all of this values to case clases and objects
  def fromHeadersAndLine(headers: Array[Header]): Converter[String, RawCSVRow] =
    new Converter[String, RawCSVRow] {
    override def map(x: String): RawCSVRow = {
      val values: List[CSVVal] = SimpleParser.toCSVVal.parseLine(x)
      if (headers.length != values.length) throw new RuntimeException("lengths mismatch")
      RawCSVRow(headers.zip(values).toMap)
    }
  }

  val textToArrayOfRawCSVRow: Converter[String, Array[RawCSVRow]] =
    new Converter[String, Array[RawCSVRow]] {
    override def map(x: String): Array[RawCSVRow] = {
      val headers = HeaderReader.fromFirstLine.extractHeaders(x)
      val lines = LineReader.fromStr.extractLines(x)
      lines.map(Converter.fromHeadersAndLine(headers).map(_))
    }
  }

  val fileNameToLazyList: Converter[String, LazyList[String]] = new Converter[String, LazyList[String]] {
    override def map(x: String): LazyList[String] = {
      try {
        val str = Source.fromFile(x).getLines()
        str.to(LazyList)
      } catch {
        case _: Exception => throw new RuntimeException("something wrong reading file")
      }
    }
  }


  def fromStrLazyListToRawCSVRowIT(headers: Array[Header]) =
    new Converter[LazyList[String], LazyList[RawCSVRow]] {
      override def map(x: LazyList[String]): LazyList[RawCSVRow] = {
        x.map(fromHeadersAndLine(headers).map)
    }
  }

  val fromHeaderToBirthdayTypedHeader = new Converter[Header, HeaderTyped] {
    override def map(x: Header): HeaderTyped = {
      x.name match {
        case "email" => HeaderTyped("email", CSVEmail.refType)
        case "date_of_birth" => HeaderTyped("date", CSVDate.refType)
        case "first_name" => HeaderTyped("first_name", CSVValStr.refType)
        case "last_name" => HeaderTyped("last_name", CSVValStr.refType)
        case _ => throw new RuntimeException("impossible to parse value")
      }
    }
  }

  val from_CSVVal_To_TypedCSVVal = new Converter[(HeaderTyped, CSVVal), CSVVal] {
    override def map(x: (HeaderTyped, CSVVal)): CSVVal = {
      ValueTyped(x._2, x._1.refType).cast
    }
  }
}
