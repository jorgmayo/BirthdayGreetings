package CSV

import CSV.Birthday.CSVEmail
import CSV.Checker.TypeChecker
import org.scalatest.FunSuite

class TypeCheckerTest extends FunSuite {
  val email1 = CSVEmail("jorge.mayoral@devo.com")
  val notemail = CSVEmail("jorge")

  test("emailTypeChecker") {
    assert(TypeChecker.emailTypeChecker.typeCheck(email1))
    assert(!TypeChecker.emailTypeChecker.typeCheck(notemail))
  }

  test("csvValTypeChecker") {
    assert(TypeChecker.csvBirthdayValTypeChecker.typeCheck(email1))
    assert(!TypeChecker.csvBirthdayValTypeChecker.typeCheck(notemail))
  }
}
