package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import models.messageboardmemory

@Singleton
class messageboard @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def login = Action {implicit request =>
    Ok(views.html.login())
  }

  def openmessageboard = Action{ implicit request =>
    Ok(views.html.messageboard(Seq("Hi","Bye"),Seq("Hola","Adios")))

    val username = request.session.get("username")
    username.map { user =>
      val generalmessage = messageboardmemory.getgeneralMessage()
      val privatemessage = messageboardmemory.getprivateMessage(user)
        Ok(views.html.messageboard(generalmessage, privatemessage))



    }.getOrElse(Redirect(routes.messageboard.login))

  }

  def validatelogin() = Action{ implicit request => 
      val postVal = request.body.asFormUrlEncoded
      postVal.map { args =>
        val username = args("Username").head
        val password = args("Password").head

        println(messageboardmemory.verifyUser(username, password))
      if (messageboardmemory.verifyUser(username,password)){
        Redirect(routes.messageboard.openmessageboard).withSession("username"-> username)
      }
      else {
        Redirect(routes.messageboard.login).flashing("error" -> "not working")
      }

    }.getOrElse(Redirect(routes.messageboard.login))
  
  }

    def createlogin = Action{
    implicit request => 
          val body = request.body.asFormUrlEncoded
          body.map{ args =>
            val username = args("Username").head
            val password = args("Password").head

          
          if (messageboardmemory.createUser(username,password)){
            Redirect(routes.messageboard.openmessageboard).withSession("username"-> username)
        
          }
          else {
            Redirect(routes.messageboard.login).flashing("error" -> "not working")
          }
        }.getOrElse(Redirect(routes.messageboard.login))


  }

  def sendMessage = Action{
    implicit request => 
    val username = request.session.get("username")
    username.map { user =>
      val postVal = request.body.asFormUrlEncoded
      postVal.map { args =>
        val message = args("message").head
        val recip = args("recip").head
          messageboardmemory.sendMessage(user, message, recip)
          Redirect(routes.messageboard.openmessageboard)
        }.getOrElse(Redirect(routes.messageboard.openmessageboard))
  }.getOrElse(Redirect(routes.messageboard.openmessageboard))
  }

  def logout = Action { implicit request =>
    Ok(views.html.login()).withNewSession  
  }
}
