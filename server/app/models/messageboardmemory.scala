package models
import collection.mutable

object messageboardmemory{
    val users = mutable.Map[String,String]("mlewis" -> "prof", "web"  -> "apps")
    val privatemessage = mutable.Map[String,List[String]]("mlewis" -> List("your","mom"))
    val generalmessage = mutable.Map[String,List[String]]("general"-> List("tu", "mama"))


def sendMessage(user:String, message:String, recip:String ):Unit={
    if(recip == ""){
        val newmessage = s"$user: $message"
        generalmessage("general") = newmessage :: generalmessage.getOrElse("general", Nil)

    }else{
        val newmessage = s"$user: $message"
        privatemessage(recip) = newmessage :: privatemessage.getOrElse(recip, Nil)

    }
}

def getprivateMessage(user:String):List[String]={
    privatemessage.getOrElse(user, Nil)

}

def getgeneralMessage():List[String]={
    generalmessage.getOrElse("general", Nil)
}

def createUser(username:String, password:String):Boolean={
    if(users.contains(username)){
            return false
        }
        users(username) = password
        return true 

    }
    



def verifyUser(username:String, password:String):Boolean={
    users.get(username).map(_== password).getOrElse(false)
    

}
}