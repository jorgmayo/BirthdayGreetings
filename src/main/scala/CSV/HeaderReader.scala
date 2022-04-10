package CSV

import Reader.Header

trait HeaderReader[Source] {
  def extractHeaders(s: Source): List[Header]
}

object HeaderReader {
  val fromFirstLine = new HeaderReader[String] {
    override def extractHeaders(s: String): List[Header] = {
      s.split("\n")(0)
        .split(",")
        .map(s => s.trim)
        .map(s => Header(s))
        .toList
    }
  }
}
