package services



import scala.concurrent.ExecutionContext.Implicits.global
import Components.EmployeeComponent
import Connection.DbProvider
import Models.Employee
import com.google.inject.Inject

import scala.concurrent.Future

class EmployeeService @Inject()( val  dbProvider:DbProvider,val employeeCompnent: EmployeeComponent){

  employeeCompnent.create
  def insert(emp: Employee) = {
    val insRes = employeeCompnent.insert(emp)
     val res = insRes.map { res => s"$res row inserted" }.recover {
     case ex: Throwable => ex.getMessage
     }
    res
  }

  def delete(exp: Double): Future[String] = {
   val delRes = employeeCompnent.delete(exp)
    val res = delRes.map { res => s"$res row deleted" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

  def updateName(id:Int,name:String): Future[String] = {
    val updRes = employeeCompnent.updateName(id,name)
    val res = updRes.map { res => s"$res row is updated" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

  def getAll = {
    val getRes = employeeCompnent.getAll
    val res = getRes.map { res => s" List of Employee\n $res" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }
  def upsert(emp: Employee) = {
    val getRes = employeeCompnent.upsert(emp)
    val res: Future[String] = getRes.map { res => s"$res row is upserted" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }


}
