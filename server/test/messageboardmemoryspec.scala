import org.scalatestplus.play.PlaySpec
import models._


class messageboardmemoryspec extends PlaySpec{
    "messageboardmemory" must {
        "do valid login for default user" in {
            messageboardmemory.verifyUser("mlewis" , "prof") mustBe (true)
        }

        "reject wrong password" in {
            messageboardmemory.verifyUser("mlewis" , "no") mustBe (false)
    }
    "reject wrong username" in {
            messageboardmemory.verifyUser("mike" , "prof") mustBe (false)
}
"create user test" in {
    messageboardmemory.createUser("mlewis" , "prof") mustBe (false)
    messageboardmemory.getgeneralMessage() mustBe (List("tu" , "mama"))
    messageboardmemory.getprivateMessage("mlewis") mustBe (List("your","mom"))

}
"get correct general message" in {
    messageboardmemory.getgeneralMessage() mustBe (List("tu", "mama"))
}
"get correct private message" in {
    messageboardmemory.getprivateMessage("mlewis") mustBe (List("your","mom"))
}
"send message general and private" in {
    messageboardmemory.sendMessage("mlewis", "testing", "web")
    messageboardmemory.getgeneralMessage() mustBe (List("tu" , "mama"))
    messageboardmemory.getprivateMessage("web") mustBe (List("mlewis: testing"))
}
}
}