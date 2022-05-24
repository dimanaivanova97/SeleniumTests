package apitests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

//POJO class for login credentials
public class LoginPOJO {
    private String usernameOrEmail;
    private String password;

    @Parameters({"username"})
    public void setUsernameOrEmail(String username) {
        this.usernameOrEmail = username;
    }

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    @Parameters({"password"})
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
