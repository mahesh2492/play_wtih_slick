package controllers

import javax.inject.Singleton

import scala.concurrent.ExecutionContext.Implicits.global
import Models.Project
import com.google.inject.Inject
import play.api.mvc.Controller
import services.ProjectService
import play.api.mvc._

@Singleton
class ProjectController @Inject()(projectService: ProjectService) extends Controller{

  def pro_insert = Action.async {
    val output =projectService.insert(Project(10,"angry bird",5,"mahesh"))
    output.map(x=>Ok(x))
  }

  def pro_delete = Action.async {
    val output =projectService.delete("codesquad")
    output.map(x=>Ok(x))
  }

  def pro_updateName = Action.async {
    val output =projectService.updateName(1,"project")
    output.map(x=>Ok(x))

  }

  def pro_getAll= Action.async {
    val output =projectService.getAll
    output.map(x=> Ok(x))

  }

  def pro_upsert = Action.async {
    val output = projectService.upsert(Project(11,"angry bird 2",5,"mahesh"))
    output.map(x=> Ok(x))
  }
}
