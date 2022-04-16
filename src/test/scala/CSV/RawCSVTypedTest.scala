package CSV

import CSV.Birthday.CSVEmail
import CSV.Checker.TypeChecker
import CSV.Raw.{CSVValStr, HeaderTyped}
import jdk.javadoc.internal.doclets.toolkit.util.Utils.Pair
import org.scalatest.FunSuite

import scala.reflect.ClassTag

class RawCSVTypedTest extends FunSuite {

  private val email: HeaderTyped = HeaderTyped("email",CSVEmail.refType)
  private val email1: CSVEmail = CSVEmail("hola")
  private val notEmail: CSVValStr = CSVValStr("notEmail")

  private val pair: (HeaderTyped, CSVEmail) = email -> email1
  private val pair2: (HeaderTyped, CSVValStr) = email -> notEmail

  test("simpleTypeCheck") {
    assert(TypeChecker.headerTypeChecker.typeCheck(pair))
    assert(!TypeChecker.headerTypeChecker.typeCheck(pair2))
  }
}
