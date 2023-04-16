package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

import static io.restassured.RestAssured.*;

public class Pet {
    @Test
    public void createPetPractice(){
        /*
        1.created a pet
        2.validated post call response body and status code
        3.Sent Get request with newly created pet id
        4.Validated get call response body and status code
         */
        baseURI="https://petstore.swagger.io";
        basePath="v2/pet";
        String petName="BeefCake";
        String petStatus="waiting";
        int petId=76834;
        Response response= given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(PayloadUtils.getPetPayload(petId,petName,petStatus)).when().post().then().statusCode(200).extract().response();


        PetPojo parsedResponse=response.as(PetPojo.class);
        Assert.assertEquals(petName,parsedResponse.getName());
        Assert.assertEquals(petId,parsedResponse.getId());
        Assert.assertEquals(petStatus,parsedResponse.getStatus());

        response=RestAssured.given().accept(ContentType.JSON)
                .when().get("https://petstore.swagger.io/v2/pet/"+petId)
                .then().statusCode(200).extract().response();

        PetPojo parsedGetResponse = response.as(PetPojo.class);

        Assert.assertEquals(petId,parsedGetResponse.getId());
        Assert.assertEquals(petName,parsedGetResponse.getName());
        Assert.assertEquals(petStatus,parsedGetResponse.getStatus());


    }

}
