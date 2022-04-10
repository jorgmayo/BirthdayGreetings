package CSV

import java.io.File
import javax.swing.JToolBar.Separator
import scala.io.Source
import scala.util.Using

trait LineReader[Source] {
  val endLineSeparator = "\n"
  val dropFirst = true;

  def extractLines(x: Source): Array[String] = {
    val str = toStr(x)
    if (dropFirst) separate(str).drop(1)
    else separate(str)
  }

  def separate(x: String) = {
    x.split(endLineSeparator)
  }
  def toStr(x: Source): String
}

object LineReader {
  val fromStr: LineReader[String] = new LineReader[String] {
    override def toStr(x: String): String = x
    }

  val fromFileName: LineReader[String] = new LineReader[String] {
    override def toStr(x: String): String = {
      try {
        val str = Using(Source.fromFile(x)) { source => source.mkString }
        str.get
      } catch {
        case _: Exception => throw new RuntimeException("something wrong reading file")
      }
    }
  }

  val fromIterator: LineReader[Iterator[String]] = new LineReader[Iterator[String]] {
    val takeNext: Int = 1
    def takeNext(x: Iterator[String]): Array[String] = x.take(takeNext).toArray
    override def toStr(x: Iterator[String]): String = x.next()
    override def extractLines(x: Iterator[String]): Array[String] = takeNext(x)
  }
}
