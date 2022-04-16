package CSV.Birthday

import CSV.Raw.CSVVal

trait BirthdayCSVVal extends CSVVal

case class CSVEmail(email: String) extends BirthdayCSVVal {
  override val refType: String = CSVEmail.refType
}

object CSVEmail {
  val refType: String = "email"
}

case class CSVFName(firstName: String) extends BirthdayCSVVal {
  override val refType: String = CSVFName.refType
}

object CSVFName {
  val refType: String = "first_name"
}

case class CSVLName(lastName: String) extends BirthdayCSVVal {
  override val refType: String = CSVLName.refType
}

object CSVLName {
  val refType: String = "last_name"
}

case class CSVDate(date: String) extends BirthdayCSVVal {
  override val refType: String = CSVDate.refType
}

object CSVDate {
  val refType: String = "date"
}
