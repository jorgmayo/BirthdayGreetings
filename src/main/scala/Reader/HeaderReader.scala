package Reader

import CSV.Raw.Header

import scala.io.Source
import scala.util.Using

trait HeaderReader[Source] {
  def extractHeaders(s: Source): Array[Header]
  def getFromLine(s: String): Array[Header] = s.split(",")
    .map(s => s.trim)
    .map(s => Header(s))
}

object HeaderReader {
  val fromFirstLine = new HeaderReader[String] {
    override def extractHeaders(s: String): Array[Header] = {
      getFromLine(s.split("\n")(0))
    }
  }
  val fromFileName = new HeaderReader[String] {
    override def extractHeaders(s: String): Array[Header] = try {
      val str = Using(Source.fromFile(s)) { source => source.getLines().next()}
      getFromLine(str.get)
    } catch {
      case _: Exception => throw new RuntimeException("something wrong reading file")
    }
  }
}
