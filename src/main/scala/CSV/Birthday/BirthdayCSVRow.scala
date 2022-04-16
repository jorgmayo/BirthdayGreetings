package CSV.Birthday

import CSV.Checker.TypeChecker
import CSV.Raw.{CSVVal, HeaderTyped}

protected case class BirthdayCSVRow(row: Map[HeaderTyped, CSVVal])

object BirthdayCSVRow {

  private def isGood(option: Option[_], where: String): Boolean = {
    if (option.isEmpty) true
    else throw new RuntimeException(s"error during $where Check")
  }

  private def checkValTypes(tuple: (HeaderTyped, CSVVal)): Boolean = {
    TypeChecker.csvBirthdayValTypeChecker.typeCheck(tuple._2)
  }

  private def checkHeaders(row: Map[HeaderTyped, CSVVal]): Boolean = {
    val rowChecked = row.map(TypeChecker.headerTypeChecker.typeCheck).find(_ == false)
    isGood(rowChecked, "header")
  }

  private def checkValues(row: Map[HeaderTyped, CSVVal]): Boolean = {
    val rowChecked = row.map(checkValTypes).find(_ == false)
    isGood(rowChecked, "values")
  }

  def withRow(row: Map[HeaderTyped, CSVVal]): BirthdayCSVRow = {
    val allGood = checkHeaders(row) && checkValues(row)
    if (allGood) BirthdayCSVRow(row)
    else throw new RuntimeException("error")
  }
}
