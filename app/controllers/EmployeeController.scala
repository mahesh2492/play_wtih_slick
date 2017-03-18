package controllers

import services.EmployeeService

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject.Singleton
import Components.EmployeeComponent
import Models.Employee
import com.google.inject.Inject
import play.api._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class EmployeeController @Inject()(employeeService:EmployeeService) extends Controller{


  def insert = Action.async {
    val output =employeeService.insert(Employee("hello",299,6.0D))
      output.map(x=>Ok(x))
  }

  def delete = Action.async {
    val output =employeeService.delete(6.0D)
    output.map(x=>Ok(x))
  }

  def updateName = Action.async {
    val output =employeeService.updateName(219,"shivangi")
    output.map(x=>Ok(x))

  }

  def getAll= Action.async {
    val output =employeeService.getAll
    output.map(x=> Ok(x))

  }

  def upsert = Action.async {
    val output =employeeService.upsert(Employee("huh",11,1.2D))
    output.map(x=> Ok(x))
  }
}
