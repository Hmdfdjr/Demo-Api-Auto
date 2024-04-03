package apiauto;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;

public class TestApiAuto {
   @Test
    public void getUserApiAuto(){
       given().when()
               .get("https://reqres.in/api/users?page=2")
               .then().log().all()
               .assertThat().statusCode(200)
               .assertThat().body("per_page", Matchers.equalTo(6))
               .assertThat().body("page", Matchers.equalTo(2));
    }
    @Test
    public void getUserApiAutoNotFound(){
       RestAssured.given().when()
               .get("https://reqres.in/api/users/23")
               .then().log().all()
               .assertThat().statusCode(404);
    }
    @Test
    public void postUserApiAuto(){
       String valueName = "morpheus";
       String valueJob = "leader";

        JSONObject bodyObj = new JSONObject();

        bodyObj.put("name", valueName);
        bodyObj.put("job", valueJob);

        given()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bodyObj.toString())
                .when()
                .post("https://reqres.in/api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .assertThat().body("name", Matchers.equalTo(valueName));
    }
    @Test
    public void putUserApiAuto(){
       RestAssured.baseURI = "https://reqres.in/";
       String userId = String.valueOf(2);
       String newName = "siskaJane";

       String fname = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.first_name");
       String lname = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.last_name");
       String avatar = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.avatar");
       String email = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.email");
        System.out.println("name before ="+fname);

        HashMap<String, String> bodyMap = new HashMap<>();
        bodyMap.put("id", userId);
        bodyMap.put("email", email);
        bodyMap.put("first_name", newName);
        bodyMap.put("last_name", lname);
        bodyMap.put("avatar", avatar);
        JSONObject jsonObject = new JSONObject(bodyMap);

        RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .put("api/users/" + userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }
    @Test
    public void patchUserApiAuto(){
       RestAssured.baseURI = "https://reqres.in/";
       String userId = String.valueOf(2);
       String newName = "siskaKolh";

       String fname = given().when().get("api/users/"+userId).getBody().jsonPath().get("data.first_name");
        System.out.println("name before"+fname);

        HashMap<String, String> bodyMap =new HashMap<>();
        bodyMap.put("first_name", newName);
        JSONObject jsonObject = new JSONObject(bodyMap);

        given().log().all()
                .header("Content-Type", "application/json")
                .body(jsonObject.toString())
                .patch("api/users/"+userId)
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body("first_name", Matchers.equalTo(newName));
    }
    @Test
    public void deleteUserApiAuto(){
       RestAssured.baseURI = "https://reqres.in/";
       String userToDelete = String.valueOf(4);

       given().log().all()
               .when().delete("api/users/"+userToDelete)
               .then().log().all()
               .assertThat().statusCode(204);

    }
}
