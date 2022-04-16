package CSV.Raw

/**
 * Un valor tipado de CSV no es más que un valor en el qeu se especifica el tipo
 * de antemano. En nuestro caso concreto, se va a usar para tipar el CSV del Birthday
 */
trait RawCSVTyped
final case class HeaderTyped(nam: String, refType: String) extends RawCSVTyped
final case class ValueTyped(value: CSVVal, refToTypeValue: String) extends RawCSVTyped
