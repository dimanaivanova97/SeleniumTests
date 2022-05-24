package apitests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UserID{
    Integer userID;

    public UserID() throws JsonProcessingException {
        LoginPOJO login = new LoginPOJO();
        login.setUsernameOrEmail("dimana.ivanova");
        login.setPassword("Dimana.97");

        ObjectMapper objectMapper = new ObjectMapper();
        String convertedJSON = objectMapper.writeValueAsString(login);

        baseURI = "http://training.skillo-bg.com:3100";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(convertedJSON)
                .when()
                .post("/users/login");
        String getLoginResponseBody = response.getBody().asString();
        this.userID = JsonPath.parse(getLoginResponseBody).read("$.user.id");
    }

    public Integer getUserID() {
        return userID;
    }
}
