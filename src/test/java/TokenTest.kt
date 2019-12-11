import com.github.project_njust.ccf_manager.FileManager
import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.wrapper.token.Token
import com.github.project_njust.ccf_manager.wrapper.token.TokenManager
import org.junit.Assert
import org.junit.Test

class TokenTest {

    init{
        TokenManager.Signature = "D414573E997FB25BA280F3CE636D4BA291362672688D334DD577CFE4F9CA2910"
    }

    @Test
    fun genToken() {
        val token = Token(
                123,
                UserType.STUDENT,
                123,
                456,
                12324123
        )
        Assert.assertEquals(token.toTokenString(),"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjEyMywidXNlclR5cGUiOiJTVFVERU5UIiwiZXhwIjoxMjMsImlhdCI6NDU2LCJqdGkiOjEyMzI0MTIzfQ.A48D8DBC88D1E9F9A29FCAF477618B1A100333C86B630C5932887C8A88C027AE")
    }
}