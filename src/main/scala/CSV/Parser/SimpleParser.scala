package CSV.Parser

import CSV.Raw.{CSVVal, CSVValFloat, CSVValInt, CSVValNull, CSVValStr}
import CSV._

/**
 * Transforma texto plano en una lista con los valores parseados
 * @tparam B es el valor de salida tras procesar el tetxto plano
 *
 * Es suficiente saber parsear un valor para poder parsear líneas enteras
 * separadas, por defecto, por una coma
 */
trait SimpleParser[B] {
  val separator = ","
  def parseLine(x: String, separator: String = separator): List[B] = {
    separate(x, separator).map(s => parseVal(s))
  }
  def separate(x: String, separator: String = separator): List[String] = {
    x.split(separator).map(_.trim).map(_.replace("\"", "")).toList
  }
  def parseVal(x: String): B
}

object SimpleParser {
  val toCSVVal = new SimpleParser[CSVVal] {

    private val int: String => Option[Int] = tryIt(Integer.parseInt)
    private val float: String => Option[Float] = tryIt(_.toFloat)

    override def parseVal(x: String): CSVVal = {
      x match {
        case "" => CSVValNull
        case s => int(s) match {
          case Some(n) => CSVValInt(n)
          case None => float(s) match {
            case Some(f) => CSVValFloat(f)
            case None => CSVValStr(s)
          }
        }
      }
    }
  }


  private def tryIt[A, B](f: String => B): String => Option[B] = {
    s => try {
      Option(f(s))
    } catch {
      case _: Throwable => Option.empty
    }
  }
}
