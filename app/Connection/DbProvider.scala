package Connection


import com.typesafe.config.ConfigFactory
import slick.jdbc._
//import slick.jdbc.JdbcProfile

class DbProvider {

  def dbType = ConfigFactory.load().getString("dbType")

  val driver: JdbcProfile = if(dbType == "mysqlDB") MySQLProfile
  else if (dbType == "myPostgresDB") PostgresProfile
  else throw new Exception("Invalid DB name")

  import driver.api._

  val db: Database = Database.forConfig(dbType)

}

