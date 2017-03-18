package Tables

import Connection.DbProvider
import Models.Project
import com.google.inject.Inject

/**
  * Created by knoldus on 14/3/17.
  */
   class ProjectTable @Inject()(val dbProvider:DbProvider,val employeeTable: EmployeeTable) {

  import dbProvider.driver.api._

  class ProjectTable(tag: Tag) extends Table[Project](tag, "project") {
    val id = column[Int]("id")

    def employeeProjectFK = foreignKey("employee_project_fk", id, employeeTable.employeeTableQuery)(_.id)

    val pname = column[String]("pname", O.PrimaryKey)

    val team_members = column[Int]("team_members")

    val lead = column[String]("lead")

    def * = (id, pname, team_members, lead) <>(Project.tupled, Project.unapply)

  }
  val projectTableQuery = TableQuery[ProjectTable]
}
