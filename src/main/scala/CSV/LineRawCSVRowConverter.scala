package CSV

import Converters.Converter
import Reader.Header

case class LineRawCSVRowConverter(line: String, headers: List[Header])
  extends Converter[String, RawCSVRow] {

}
