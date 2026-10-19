package quickchat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class LoginTest {
    Login valid(){return new Login("Kyle","Smith","kyl_1","Ch&&sec@ke99!","+27838968976");}
    @Test void usernameExamples(){
        assertTrue(valid().checkUserName()); assertEquals(Login.USER_OK,valid().usernameStatus());
        Login bad=new Login("Kyle","Smith","kyle!!!!!!!","password","08966553");
        assertFalse(bad.checkUserName());assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",bad.registerUser());
    }
    @Test void passwordExamples(){
        assertTrue(valid().checkPasswordComplexity());assertEquals("Password successfully captured.",valid().passwordStatus());
        Login bad=new Login("Kyle","Smith","kyl_1","password","+27838968976");
        assertFalse(bad.checkPasswordComplexity());assertEquals(Login.PASS_BAD,bad.registerUser());
    }
    @Test void passwordRequirementsIndividually(){
        for(String password:new String[]{"Ab1!","abcdefgh1!","Abcdefgh!","Abcdefgh1","Abcdefg1 "})
            assertFalse(new Login("A","B","a_1",password,"+27838968976").checkPasswordComplexity(),password);
    }
    @Test void phoneExamples(){
        assertTrue(valid().checkCellPhoneNumber());assertEquals(Login.PHONE_OK,valid().phoneStatus());
        Login bad=new Login("Kyle","Smith","kyl_1","Ch&&sec@ke99!","08966553");
        assertFalse(bad.checkCellPhoneNumber());assertEquals(Login.PHONE_BAD,bad.registerUser());
        assertFalse(Login.validPhone("+270838968976"));assertFalse(Login.validPhone("+2783896897"));
    }
    @Test void successfulLogin(){
        Login login=valid(); assertEquals("User registered successfully.",login.registerUser());
        login.enterCredentials("kyl_1","Ch&&sec@ke99!");assertTrue(login.loginUser());
        assertEquals("Welcome Kyle, Smith it is great to see you again.",login.returnLoginStatus());
    }
    @Test void failedLogin(){
        Login login=valid();login.registerUser();login.enterCredentials("kyl_1","wrong");assertFalse(login.loginUser());
        assertEquals("Username or password incorrect, please try again.",login.returnLoginStatus());
        login.enterCredentials("wrong","Ch&&sec@ke99!");assertFalse(login.loginUser());
    }
    @Test void cannotLoginBeforeRegistration(){Login l=valid();l.enterCredentials("kyl_1","Ch&&sec@ke99!");assertFalse(l.loginUser());}
}
