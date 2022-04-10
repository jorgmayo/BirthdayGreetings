package Reader

sealed trait CSVVal
final case class CSVValInt(x: Int) extends CSVVal
final case class CSVValStr(x: String) extends CSVVal
final case class CSVValFloat(x: Double) extends CSVVal
final object CSVValNull extends CSVVal