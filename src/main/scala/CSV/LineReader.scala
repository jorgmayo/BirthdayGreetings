package CSV

import java.io.File
import javax.swing.JToolBar.Separator
import scala.io.Source
import scala.util.Using

trait LineReader[Source] {
  val endLineSeparator = "\n"
  val dropFirst = true;

  //FIXME: turn this into a matrix
  def extractLines(x: Source): List[String] = {
    val str = toStr(x)
    if (dropFirst) separate(str).drop(1)
    else separate(str)
  }

  def separate(x: String) = {
    x.split(endLineSeparator).toList
  }
  def toStr(x: Source): String
}

object LineReader {
  val fromStr: LineReader[String] = new LineReader[String] {
    override def toStr(x: String): String = x
    }

  val fromFileName: LineReader[String] = new LineReader[File] {
    override def toStr(x: String): String = {
      try {
        val str = Using(Source.fromFile("file.txt")) { source => source.mkString }
        str.get
      } catch {
        case _: Exception => throw new RuntimeException("something wrong reading file")
      }
    }
  }
}
