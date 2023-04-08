package get;

import io.restassured.RestAssured;
import org.junit.Test;

public class StarWars {
    /*
    1 defined/determined the endpoint
    2 added query string params as needed
    3 defined HTTP method
    4 Send
    5 Validate status code
     */
    @Test
    public void getStarWarsChars(){
        RestAssured.given()
                .when().get("https://swapi.dev/api/people")
                .then()
                .statusCode(200)
                .log().body();
    }

}
