package Components

import Connection.{DbProvider}
import Models.Project
import Tables.{ ProjectTable}
import com.google.inject.Inject


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class ProjectComponent @Inject()(val dbProvider:DbProvider,val projectable: ProjectTable) {

  import dbProvider.driver.api._
  def create = dbProvider.db.run(projectable.projectTableQuery.schema.create)


  def insert(prj: Project) = dbProvider.db.run {
    projectable.projectTableQuery += prj
  }

  def delete(pname: String) = {
    val query = projectable.projectTableQuery.filter(x => x.pname === pname)
    val action = query.delete
    dbProvider.db.run(action)
  }

  def updateName(id:Int,name:String):Future[Int] = {
    val query =projectable.projectTableQuery.filter(_.id === id).map(_.pname).update(name)
    dbProvider.db.run(query)
  }

  def getAll: Future[List[Project]] = { dbProvider.db.run { projectable.projectTableQuery.to[List].result}}

 def upsert(emp : Project) = {
   //    val data: List[Project] = Await.result(getAll,Duration.Inf)
   //
   //    val flag: List[Boolean] = data.map(x => if(x.pname == emp.pname) true else false)
   //
   //    if(flag.contains(true))
   //    {
   //
   //
   //      val action = projectable.projectTableQuery.filter(_.pname === emp.pname).map(x => (x.team_members,x.lead)).update((emp.team_members,emp.lead))
   //      dbProvider.db.run(action)
   //    }
   //    else {
   //      val action: Future[Int] = insert(emp)
   //
   //    }
   dbProvider.db.run {
     projectable.projectTableQuery.insertOrUpdate(emp)
   }

 }
//  def sortByProjectName() =  {
//    projectable.projectTableQuery.sortBy(_.pname)
//  }
}

