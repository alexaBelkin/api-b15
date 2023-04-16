package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.ResultPojo;
import pojo.StarWarsPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarWars {
    /*
    1 defined/determined the endpoint
    2 added query string params as needed
    3 defined HTTP method
    4 Send
    5 Validate status code
     */
    @Test
    public void getStarWarsChars() {
        RestAssured.given()
                .when().get("https://swapi.dev/api/people")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getSWCharsDeserialized() {
        RestAssured.baseURI="https://swapi.dev";
        RestAssured.basePath="api/people";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200).extract().response();
        Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
        });
        int count = (int) deserializedResponse.get("count");
        Assert.assertEquals(82, count);
        //Array of Json objects[{},{},{}]
        List<Map<String, Object>> results = (List<Map<String, Object>>) deserializedResponse.get("results");

        //validate is page has characters and then summ and go to the next page (we have total of 82 characters)
        //2--> get list of all SW characters and store it in the list
        //LVL100:Find only charartcers gender is female (as list) (Map<String,List<String>>-->female: name)
        int busket = 0;
        List<Object> listOfSWcharcters = new ArrayList<>();
        Map<String, String> onlyFemales = new HashMap<>();
        do {
            deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {
            });
            List<Object> page = (List<Object>) deserializedResponse.get("results");
            for (int i = 0; i < page.size(); i++) {
                Map<String, String> names = (Map<String, String>) page.get(i);
                if (names.get("gender").equals("female")) {
                    onlyFemales.put(names.get("name"), names.get("gender"));
                }
                listOfSWcharcters.add(names.get("name"));
            }

            int countPeople = page.size();
            busket += countPeople;
            if (deserializedResponse.get("next") != null) {
                response = RestAssured.given().header("Accept", "application/json")
                        .when().get((String) deserializedResponse.get("next"))
                        .then().statusCode(200).extract().response();
            }
        } while (((String) deserializedResponse.get("next")) != null);
        Assert.assertEquals(busket, count);
        System.out.println(listOfSWcharcters);
        System.out.println(onlyFemales);

    }

    @Test
    public void swapiGetWithPojo() {
        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get("https://swapi.dev/api/people/")
                .then().statusCode(200).extract().response();
        StarWarsPojo deserializedResp = response.as(StarWarsPojo.class);
        int actualCount = deserializedResp.getCount();
        int expectedCount = 82;
        Assert.assertEquals(expectedCount, actualCount);// unrecognizedpropertiexection-one kewy inJSONis missing in your POJO CLASS
        List<ResultPojo> results = deserializedResp.getResults();
        for (ResultPojo result : results) {
            System.out.println(result.getName());
        }

    }

    @Test
    public void HomeworkStarwarsWithPojo(){
        RestAssured.baseURI="https://swapi.dev";
        RestAssured.basePath="api/people";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().statusCode(200).extract().response();

       StarWarsPojo deserelizedResp=response.as(StarWarsPojo.class);
       int actualTotalCharactersCount=deserelizedResp.getResults().size();
       String nextUrl=deserelizedResp.getNext();

       while (nextUrl!=null){

           response = RestAssured.given().accept(ContentType.JSON)
                   .when().get(nextUrl)
                   .then().statusCode(200)
                   .contentType(ContentType.JSON)//validating response format
                   .extract().response();
           deserelizedResp= response.as(StarWarsPojo.class);
          actualTotalCharactersCount+= deserelizedResp.getResults().size();
         nextUrl= deserelizedResp.getNext();

       }
       Assert.assertEquals(deserelizedResp.getCount(),actualTotalCharactersCount);



    }
}
