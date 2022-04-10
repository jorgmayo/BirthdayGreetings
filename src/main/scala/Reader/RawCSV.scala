package Reader

sealed trait RawCSV
final case class Header(name: String) extends RawCSV
final case class Value(value: CSVVal) extends RawCSV
