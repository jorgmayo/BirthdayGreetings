package CSV.Raw

import CSV.Birthday.{CSVEmail, CSVFName, CSVLName}

/**
 * Un valor tipado de CSV no es más que un valor en el qeu se especifica el tipo
 * de antemano. En nuestro caso concreto, se va a usar para tipar el CSV del Birthday
 */
trait RawCSVTyped
final case class HeaderTyped(nam: String, refType: String) extends RawCSVTyped
final case class ValueTyped(value: CSVVal, refToTypeValue: String) extends RawCSVTyped {
  def cast: CSVVal = {
    value match {
      case CSVValStr(x) => refToTypeValue match {
        case "str" => CSVValStr(x)
        case "email" => CSVEmail(x)
        case "first_name" => CSVFName(x)
        case "last_name" => CSVLName(x)
      }
      case _ => throw new RuntimeException(s"Unsupported cating on type ${value.refType}")
    }
  }
}
