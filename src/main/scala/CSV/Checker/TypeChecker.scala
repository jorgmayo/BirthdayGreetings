package CSV.Checker

import CSV.Birthday.{CSVDate, CSVEmail, CSVFName, CSVLName}
import CSV.Raw.{CSVVal, CSVValStr, HeaderTyped}

trait TypeChecker[A] {
  def typeCheck(value: A): Boolean
}

object TypeChecker {

  val csvBirthdayValTypeChecker = new TypeChecker[CSVVal] {
    override def typeCheck(value: CSVVal): Boolean = {
      typeRegistry(value.refType).typeCheck(value)
    }
  }
  val emailTypeChecker = new TypeChecker[CSVVal] {
    private val email = ".*@.*".r
    override def typeCheck(value: CSVVal): Boolean = {
      value match {
        case s : CSVEmail => email.matches(s.email)
        case _ => false
      }
    }
  }

  val dateTypeChecker = new TypeChecker[CSVVal] {
    override def typeCheck(value: CSVVal): Boolean = {
      value match {
        case s : CSVDate => true
        case _ => false
      }
    }
  }

  val firstNameTypeChecker = new TypeChecker[CSVVal] {
    override def typeCheck(value: CSVVal): Boolean = {
      value match {
        case s : CSVFName => true
        case _ => false
      }
    }
  }

  val lastNameTypeChecker = new TypeChecker[CSVVal] {
    override def typeCheck(value: CSVVal): Boolean = {
      value match {
        case s : CSVLName => true
        case _ => false
      }
    }
  }

  val typeRegistry: Map[String, TypeChecker[CSVVal]] = Map(
  CSVEmail.refType -> emailTypeChecker,
  CSVFName.refType -> firstNameTypeChecker,
  CSVLName.refType -> lastNameTypeChecker,
  CSVDate.refType -> dateTypeChecker
  )

  val headerTypeChecker = new TypeChecker[(HeaderTyped, CSVVal)] {
    def typeCheck(value: (HeaderTyped, CSVVal)): Boolean = {
      value._1.refType == value._2.refType
    }
  }

}
