package Converters

import CSV.Birthday.{BirthdayCSVRow, BirthdayCSVVal}
import CSV.Checker.TypeChecker
import CSV.Raw.{CSVVal, Header, HeaderTyped, RawCSVRow}
import Reader.HeaderReader

case object FromFileNameToBirthdayCSVRows extends Converter[String, LazyList[BirthdayCSVRow]] {

  override def map(filename: String): LazyList[BirthdayCSVRow] = {
    val strList: LazyList[String] = Converter.fileNameToLazyList.map(filename)
    val headers: Array[Header] = HeaderReader.fromLazyList.extractHeaders(strList)
    val csvRawRow: LazyList[RawCSVRow] = Converter.fromLazyListToRawCSVRowLazyList(headers).map(strList.drop(1))
    val convertRow: LazyList[Map[HeaderTyped, CSVVal]] = csvRawRow.map(
      x => x.row.map(z =>
        (Converter.fromHeaderToBirthdayTypedHeader.map(z._1),
          Converter.from_CSVVal_To_TypedCSVVal.map(Converter.fromHeaderToBirthdayTypedHeader.map(z._1), z._2))))
    convertRow.map(z => BirthdayCSVRow.withRow(z))
  }
}
