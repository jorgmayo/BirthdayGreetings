package Converters

import CSV.{HeaderReader, LineReader, RawCSVRow, SimpleParser}
import Reader.{CSVVal, Header, RawCSV}

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
        val str = Using(Source.fromFile(x)) { source => source.getLines()}
        str.get
      } catch {
        case _: Exception => throw new RuntimeException("something wrong reading file")
      }
    }
  }
}
