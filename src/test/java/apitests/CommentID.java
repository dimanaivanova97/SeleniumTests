package apitests;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class CommentID {

    Integer commentId;

    public CommentID(Integer postId) {
        baseURI = "http://training.skillo-bg.com:3100";

        Response response = given()
                .when()
                .get("/posts/" + postId + "/comments");

        String getPublicPostsResponseBody = response.getBody().asString();
        this.commentId = JsonPath.parse(getPublicPostsResponseBody).read("$.[0].id");

        response
                .then()
                .statusCode(200)
                .body(not(empty()));
    }

    public Integer getCommentId() {
        return commentId;
    }
}
