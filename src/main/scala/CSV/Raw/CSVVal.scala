package CSV.Raw

trait CSVVal {
  val refType: String
}

final case class CSVValInt(x: Int) extends CSVVal {
  override val refType: String = CSVValInt.refType
}

object CSVValInt {
  val refType = "int"
}

final case class CSVValStr(x: String) extends CSVVal {
  override val refType: String = CSVValStr.refType
}

object CSVValStr {
  val refType = "str"
}

final case class CSVValFloat(x: Double) extends CSVVal {
  override val refType: String = CSVValFloat.refType
}

object CSVValFloat {
  val refType: String = "str"
}

object CSVValNull extends CSVVal {
  override val refType: String = "null"
}