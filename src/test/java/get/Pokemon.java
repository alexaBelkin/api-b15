package get;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PokemonPojo;
import pojo.PokemonPojoResAbil;
import pojo.PokemonResultPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pokemon {


    @Test
    public void getPokemonWithPojo(){
//        1. GET https://pokeapi.co/api/v2/pokemon?limit=100
//        2. Deserialize response using POJO classes
//        3. Validate count = 1279
//        4. Find url for pikachu
//        5. Validate that we got 100 pokemons
        RestAssured.baseURI="https://pokeapi.co/api/v2/pokemon";
        Response response = RestAssured.given().header("Accept", "application/json").queryParam("limit","100")
                .when().get()
                .then().statusCode(200).extract().response();

        PokemonPojo deserializedResp = response.as(PokemonPojo.class);
        int actual=deserializedResp.getCount();
        int expected=1279;
        Assert.assertEquals(expected,actual);
        List<PokemonResultPojo> results=deserializedResp.getResults();
        for(PokemonResultPojo result:results){
            if(result.getName().equals("pikachu")){
                System.out.println(result.getUrl());
            }

        }
        Assert.assertEquals(100,results.size());

    }

    @Test
    public void homeworkPokemonPojo(){
        /*
        //1. GET https://pokeapi.co/api/v2/pokemon
//2. Validate you got 20 pokemons
//3. Get every pokemons ability and store those in Map<String, List<String>>
         */
        RestAssured.baseURI="https://pokeapi.co/api/v2/pokemon";
        Response response = RestAssured.given().header("Accept", "application/json")
                .when().get()
                .then().statusCode(200).extract().response();
        PokemonPojo deserializedResp = response.as(PokemonPojo.class);

        List<PokemonResultPojo> results=deserializedResp.getResults();
        Map<String,List<String>> pokemonsWithAbilities=new HashMap<>();

            int pokemonsOnPage=results.size();
            int expected=20;

            for(int i=0;i<results.size();i++){
                response = RestAssured.given().header("Accept", "application/json")
                        .when().get(results.get(i).getUrl())
                        .then().statusCode(200).extract().response();
                PokemonPojoResAbil abilities=response.as(PokemonPojoResAbil.class);

                List<Map<String,Object>> list=abilities.getAbilities();
                List<String> test=new ArrayList<>();
                for(int k=0;k<list.size();k++){



                }

            }

            Assert.assertEquals(expected,pokemonsOnPage);
        System.out.println(pokemonsWithAbilities);


    }

}
