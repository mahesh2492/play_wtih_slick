package Components


import Connection.DbProvider
import Models.Employee
import Tables.EmployeeTable
import com.google.inject.Inject


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class EmployeeComponent @Inject()( val  dbProvider:DbProvider,val emplyeeTable: EmployeeTable)  {

import dbProvider.driver.api._


  def create = dbProvider.db.run(emplyeeTable.employeeTableQuery.schema.create)

  def insert(emp : Employee): Future[Int] = dbProvider.db.run {
    emplyeeTable.employeeTableQuery += emp
  }


  def delete(exp: Double): Future[Int] = {
    val query = emplyeeTable.employeeTableQuery.filter(x => x.experience === exp)
    val action = query.delete
    dbProvider.db.run(action)
  }

  def updateName(id:Int,name:String):Future[Int] = {
    val query = emplyeeTable.employeeTableQuery.filter(_.id === id).map(_.name).update(name)
    dbProvider.db.run(query)
  }


  def getAll: Future[List[Employee]] = { dbProvider.db.run { emplyeeTable.employeeTableQuery.to[List].result}}

  def upsert(emp : Employee): Future[Int] = {
    //  removing  EmployeeComponent.
//    val data: List[Employee] = Await.result(getAll,Duration.Inf)
//
//    val flag: List[Boolean] = data.map(x => if(x.id == emp.id) true else false)
//
//    if(flag.contains(true))
//    {
//
//
//      val action = emplyeeTable.employeeTableQuery.filter(_.id === emp.id).map(x => (x.name,x.experience)).update((emp.name,emp.experience))
//      dbProvider.db.run(action)
//    }
//    else {
//      val action: Future[Int] = insert(emp)
//    }
    dbProvider.db.run {
      emplyeeTable.employeeTableQuery.insertOrUpdate(emp)
    }

  }



}



