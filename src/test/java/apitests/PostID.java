package apitests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class PostID {
    Integer postID;

    public PostID() {
        baseURI = "http://training.skillo-bg.com:3100";

        Response response = given()
                .when()
                .get("/posts/public?take=5&skip=0");

        String getPublicPostsResponseBody = response.getBody().asString();
        this.postID = JsonPath.parse(getPublicPostsResponseBody).read("$[0].id");

        response
                .then()
                .statusCode(200)
                .body(not(empty()));

    }

    public Integer getPostID() {
        return postID;
    }
}
