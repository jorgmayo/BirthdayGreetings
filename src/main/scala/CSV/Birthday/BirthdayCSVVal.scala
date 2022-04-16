package CSV.Birthday

import CSV.Raw.{CSVVal, CSVValStr}

trait BirthdayCSVVal extends CSVVal

case class CSVEmail(email: String) extends BirthdayCSVVal {
  override val refType: String = CSVEmail.refType
}

object CSVEmail {
  val refType: String = "email"
}

case class CSVFName(firstName: String) extends BirthdayCSVVal {
  override val refType: String = CSVValStr.refType
}

object CSVFName {
  val refType: String = CSVValStr.refType
}

case class CSVLName(lastName: String) extends BirthdayCSVVal {
  override val refType: String = CSVValStr.refType
}

object CSVLName {
  val refType: String = CSVValStr.refType
}

case class CSVDate(date: String) extends BirthdayCSVVal {
  override val refType: String = CSVDate.refType
}

object CSVDate {
  val refType: String = "date"
}
