package Components

import Connection.{DbProvider}
import Models.Dependent
import Tables.{DependentTable}
import com.google.inject.Inject

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


class DependentComponent @Inject()( val dbProvider: DbProvider,val dependentTable: DependentTable) {


  import dbProvider.driver.api._
  def create = dbProvider.db.run(dependentTable.dependentTableQuery.schema.create)

  def insert(dep: Dependent) = dbProvider.db.run {
    dependentTable.dependentTableQuery += dep
  }

  def delete(relationStatus: String ) {
    val query = dependentTable.dependentTableQuery.filter((x => x.relationship === relationStatus)).delete
    dbProvider.db.run(query)
  }


  def update(id: Int,relationshipStatus: String) = {
    val query = dependentTable.dependentTableQuery.filter(x => x.emp_id === id).map(_.relationship).update(relationshipStatus)
    dbProvider.db.run(query)
  }

  def getAll : Future[List[Dependent]] = dbProvider.db run {
    dependentTable.dependentTableQuery.to[List].result
  }

  def upsert(dep : Dependent) {
    val res: List[Dependent] = Await.result(getAll,Duration.Inf)
    val flag = res.map(x => if(x.name == dep.name) true else false)
    if(flag.contains(true)){
      val query = dependentTable.dependentTableQuery.filter(_.name === dep.name).map(x => (x.relationship,x.age)).update((dep.relationship,dep.age))
      dbProvider.db.run(query)
    }
    else{
      dbProvider.db.run{
        dependentTable.dependentTableQuery += dep
      }
    }

  }
//  def sortByDependentName() =  {
//    dependentTable.dependentTableQuery.sortBy(_.name)
//  }



}