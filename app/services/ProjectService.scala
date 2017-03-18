package services

import Components.ProjectComponent
import Models.Project
import com.google.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import Connection.DbProvider


class ProjectService @Inject()( val  dbProvider:DbProvider,val projectCompnent: ProjectComponent) {

   projectCompnent.create

  def insert(emp: Project) = {
    val insRes = projectCompnent.insert(emp)
    val res = insRes.map { res => s"$res row inserted" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

  def delete(pname :String): Future[String] = {
    val delRes = projectCompnent.delete(pname)
    val res = delRes.map { res => s"$res row deleted" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

  def updateName(id:Int,name:String): Future[String] = {
    val updRes = projectCompnent.updateName(id,name)
    val res = updRes.map { res => s"$res row is updated" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

  def getAll = {
    val getRes: Future[List[Project]] = projectCompnent.getAll
    val res = getRes.map { res => s" List of Employee\n $res" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

  def upsert(emp: Project) = {
    val getRes = projectCompnent.upsert(emp)
    val res: Future[String] = getRes.map { res => s"$res row is upserted" }.recover {
      case ex: Throwable => ex.getMessage
    }
    res
  }

}
