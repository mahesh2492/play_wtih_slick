package Tables

import Connection.DbProvider
import Models.Employee
import com.google.inject.Inject

class EmployeeTable @Inject()( val db:DbProvider){

  import db.driver.api._
  class EmployeeTable(tag: Tag) extends Table[Employee](tag, "experienced_employee") {
    val id = column[Int]("id",O.PrimaryKey)

    val name = column[String]("name")

    val experience = column[Double]("experience")

    def * = (name,id,experience) <> (Employee.tupled, Employee.unapply)

  }
  val employeeTableQuery = TableQuery[EmployeeTable]
}