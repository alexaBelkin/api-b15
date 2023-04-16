package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ReqRes {

    @Test
    public void ReqRes(){
        //deserialization json --> java
//serialization java --> json



        Response response= RestAssured.given().header("Accept","application/json")
                .when().get("https://reqres.in/api/users?page=2")
                .then().statusCode(200)
                .extract().response();
        Map<String,Object> deseriaalizedResponse=response.as(new TypeRef<Map<String, Object>>() {
        });
        System.out.println(deseriaalizedResponse.get("per_page"));
        System.out.println(deseriaalizedResponse.get("total_pages"));
        System.out.println(deseriaalizedResponse.get("support"));
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) deseriaalizedResponse.get("data");
//System.out.println(dataList);
        for (int i = 0;i<dataList.size();i++){
            System.out.println(dataList.get(i).get("email"));
            System.out.println(dataList.get(i).get("avatar"));
        }

    }
}
