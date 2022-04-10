package Converters

import CSV.{RawCSVRow, SimpleParser}
import Reader.{CSVVal, Header, RawCSV}

/**
 * Transforma un tipo A en un tipo B
 */
trait Converter[A, B] {
  def map(x: A): B = throw new NotImplementedError("not implemented")
}

object Converter {
  def fromHeadersAndLine(headers: List[Header]): Converter[String, RawCSVRow] =
    new Converter[String, RawCSVRow] {
    override def map(x: String): RawCSVRow = {
      val values: List[CSVVal] = SimpleParser.toCSVVal.parseLine(x)
      if (headers.length != values.length) throw new RuntimeException("lengths mismatch")
      RawCSVRow(headers.zip(values).toMap)
    }
  }
}
