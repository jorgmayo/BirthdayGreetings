package Converters

import CSV.Birthday.{CSVDate, CSVEmail, CSVFName, CSVLName}
import CSV.Parser.SimpleParser
import CSV.Raw.{CSVVal, CSVValStr, Header, HeaderTyped, RawCSVRow}
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

  val fileNameToIterator: Converter[String, Iterator[String]] = new Converter[String, Iterator[String]] {
    override def map(x: String): Iterator[String] = {
      try {
        val str = Source.fromFile(x).getLines()
        str.drop(1)
      } catch {
        case _: Exception => throw new RuntimeException("something wrong reading file")
      }
    }
  }

  val fileNameToRawCSVRowIT = new Converter[String, Iterator[RawCSVRow]] {
    override def map(x: String): Iterator[RawCSVRow] = {
      val headers = HeaderReader.fromFileName.extractHeaders(x)
      val lineIt = Converter.fileNameToIterator.map(x)
      fromStrItToRawCSVRowIT(headers).map(lineIt)
    }
  }

  def fromStrItToRawCSVRowIT(headers: Array[Header]) =
    new Converter[Iterator[String], Iterator[RawCSVRow]] {
      override def map(x: Iterator[String]): Iterator[RawCSVRow] = {
        x.map(fromHeadersAndLine(headers).map(_))
    }
  }

  val fromHeaderToBirthdayTypedHeader = new Converter[Header, HeaderTyped] {
    override def map(x: Header): HeaderTyped = {
      x.name match {
        case "email" => HeaderTyped("email", CSVEmail.refType)
        case "date" => HeaderTyped("date", CSVDate.refType)
        case "first_name" => HeaderTyped("first_name", CSVValStr.refType)
        case "last_name" => HeaderTyped("last_name", CSVValStr.refType)
        case _ => throw new RuntimeException("impossible to parse value")
      }
    }
  }
}
