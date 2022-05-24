package apitests;

import static io.restassured.RestAssured.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class APITests {

    String token;
    PostID postID = new PostID();
    Integer myPostId = postID.getPostID();
//    Register a user (This would throw an error "username already used" due to the DELETE /user/userId endpoint not deleting the user)
//    @BeforeClass
//    @Parameters({"username", "password", "email", "birthDate", "publicInfo"})
//    public void register(String username, String password, String email, String birthDate, String publicInfo) throws JsonProcessingException {
//        UserCredentials registerBody = new UserCredentials();
//
//        registerBody.setUsername(username);
//        registerBody.setPassword(password);
//        registerBody.setEmail(email);
//        registerBody.setBirthDate(birthDate);
//        registerBody.setPublicInfo(publicInfo);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String convertedJSON = objectMapper.writeValueAsString(registerBody);
//
//        baseURI = "http://training.skillo-bg.com:3100";
//
//        given()
//                .header("Content-Type", "application/json")
//                .body(convertedJSON)
//                .when()
//                .post("/users")
//                .then()
//                .log()
//                .all();
//    }

    @BeforeTest
    @Parameters({"username", "password"})
    public void loginTest(String username, String password) throws JsonProcessingException {
        //Create new LoginPOJO with the credentials for the login
        LoginPOJO login = new LoginPOJO();
        login.setUsernameOrEmail(username);
        login.setPassword(password);

        //Convert POJO object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String convertedJSON = objectMapper.writeValueAsString(login);

        baseURI = "http://training.skillo-bg.com:3100";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(convertedJSON)
                .when()
                .post("/users/login");

        response
                .then()
                .statusCode(201)
                .body(not(empty()));

        //Convert the response body json into string

        String loginResponseBody = response.getBody().asString();

        token = JsonPath.parse(loginResponseBody).read("$.token");
    }

    @Test (priority = 2)
    public void likePost(){
        baseURI = "http://training.skillo-bg.com:3100";

        ActionsPOJO likePost = new ActionsPOJO();
        likePost.setAction("likePost");

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(likePost)
                .when()
                .patch("/posts/" + myPostId)
                .then()
                .statusCode(200)
                .assertThat().body(notNullValue())
                .body("post.id", equalTo(myPostId))
                .log()
                .all();
    }

    @Test (priority = 3)
    public void commentPost(){
        baseURI = "http://training.skillo-bg.com:3100";

        ActionsPOJO commentPost = new ActionsPOJO();
        commentPost.setContent("My new comment!");

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(commentPost)
                .when()
                .post("/posts/" + myPostId + "/comment")
                .then()
                .statusCode(201)
                .assertThat().body(notNullValue())
                .body("content", equalTo(commentPost.getContent()))
                .log()
                .all();
    }

    @Test (priority = 4)
    public void deleteCommentWithoutAuthentication(){
        baseURI = "http://training.skillo-bg.com:3100";
        CommentID commentID = new CommentID(myPostId);

        given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/posts/" + myPostId + "/comments/" + commentID.getCommentId())
                .then()
                .statusCode(401)
                .assertThat().body(notNullValue())
                .body("error", equalTo("Unauthorized"))
                .log()
                .all();
    }

    @Test (priority = 5)
    public void deleteComment(){
        baseURI = "http://training.skillo-bg.com:3100";
        CommentID commentID = new CommentID(myPostId);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/posts/" + myPostId + "/comments/" + commentID.getCommentId())
                .then()
                .statusCode(200)
                .assertThat().body(notNullValue())
                .body("user.isDeleted", equalTo(true))
                .body("id", equalTo(commentID.getCommentId()))
                .log()
                .all();
    }

    @Test (priority = 6)
    public void createPost() throws JsonProcessingException {
        baseURI = "http://training.skillo-bg.com:3100";

        PostPOJO createPostBody = new PostPOJO();
        createPostBody.setCaption("Burgas");
        createPostBody.setCoverUrl("https://i.imgur.com/LHnLuoJ.jpg");
        createPostBody.setPostStatus("public");

        ObjectMapper objectMapper = new ObjectMapper();
        String convertedJSON = objectMapper.writeValueAsString(createPostBody);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(convertedJSON)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .assertThat().body(notNullValue())
                .body("id", notNullValue())
                .body("caption", equalTo(createPostBody.getCaption()))
                .body("coverUrl", equalTo(createPostBody.getCoverUrl()))
                .body("postStatus", equalTo(createPostBody.getPostStatus()))
                .log()
                .all();
    }

    @Test (priority = 7)
    public void editPost() throws JsonProcessingException {
        baseURI = "http://training.skillo-bg.com:3100";

        PostPOJO editPostBody = new PostPOJO();
        editPostBody.setCaption("Burgas Burgas Burgas");
        editPostBody.setPostStatus("private");

        ObjectMapper objectMapper = new ObjectMapper();
        String convertedJSON = objectMapper.writeValueAsString(editPostBody);

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(convertedJSON)
                .when()
                .put("/posts/" + postID.getPostID())
                .then()
                .statusCode(200)
                .assertThat().body(notNullValue())
                .body("caption", equalTo(editPostBody.getCaption()))
                .body("postStatus", equalTo(editPostBody.getPostStatus()))
                .log()
                .all();
    }

    @Test (priority = 8)
    public void logout(){
        baseURI = "http://training.skillo-bg.com:3100";

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/users/logout")
                .then()
                .statusCode(200)
                .assertThat().body(notNullValue())
                .body("msg", equalTo("User successfully logged out."))
                .log()
                .all();
    }

    @Test (priority = 9)
    public void deleteUser() throws JsonProcessingException {
        baseURI = "http://training.skillo-bg.com:3100";
        UserID userID = new UserID();
        Integer myUserId = userID.getUserID();

        given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/users/" + myUserId)
                .then()
                .statusCode(200)
                .assertThat().body(notNullValue())
                .body("isDeleted", equalTo(true))
                .body("id", equalTo(myUserId))
                .log()
                .all();
    }
}
