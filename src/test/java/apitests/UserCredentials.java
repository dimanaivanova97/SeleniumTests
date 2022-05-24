package apitests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UserCredentials {

    String username;
    String email;
    String birthDate;
    String password;
    String publicInfo;

    public String getUsername() {
        return username;
    }

    @Parameters({"username"})
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    @Parameters({"email"})
    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Parameters({"birthDate"})
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    @Parameters({"password"})
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicInfo() {
        return publicInfo;
    }

    @Parameters({"publicInfo"})
    public void setPublicInfo(String publicInfo) {
        this.publicInfo = publicInfo;
    }
}
