package get;

import io.restassured.RestAssured;
import org.junit.Test;

public class ReqRes {

    @Test
    public void ReqRes(){

        RestAssured.given().header("Accept","application/json")
                .when().get("https://reqres.in/api/users?page=2")
                .then().statusCode(200)
                .extract().response();
    }
}
