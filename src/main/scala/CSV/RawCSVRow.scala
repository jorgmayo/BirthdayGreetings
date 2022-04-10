package CSV

import Reader.{CSVVal, Header}

case class RawCSVRow(map: Map[Header, CSVVal])