package Tables

import Connection.DbProvider
import Models.Dependent
import com.google.inject.Inject

/**
  * Created by knoldus on 14/3/17.
  */
class DependentTable @Inject()( val db:DbProvider,val employeeTable: EmployeeTable) {

  import db.driver.api._
  class DependentTable(tag: Tag) extends Table[Dependent](tag, "employee_dependents") {
    val emp_id = column[Int]("emp_id")
    val name = column[String]("name", O.PrimaryKey)
    val relationship = column[String]("relationship")
    val age = column[Option[Int]]("age")

    def dependentEmployeeFK = foreignKey("dependent_employee_fk", emp_id, employeeTable.employeeTableQuery)(_.id)

    def * = (emp_id, name, relationship, age) <>(Dependent.tupled, Dependent.unapply)

  }
  val dependentTableQuery = TableQuery[DependentTable]

}
