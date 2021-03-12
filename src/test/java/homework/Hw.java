package homework;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class Hw {
static {
    baseURI = "http://54.221.67.136:1000/ords/hr";
}
    @Test
    public void test1(){
        given().accept(ContentType.JSON)
                .and().queryParam("q","{\"country_id\":\"US\"}")
                .when().get("/countries")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("items.country_id[0]",equalTo("US"))
                .and().body("items.country_name[0]",equalTo("United States of America"))
                .and().body("items.region_id[0]",equalTo(2));


    }
    @Test
    public void test2(){
        given().accept("application/json")
                .and().queryParam("q","{\"department_id\":80}")
                .when().get("/employees")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("count",equalTo(25));
        Response response = given().accept("application/json")
                .and().queryParam("q","{\"department_id\":80}")
                .when().get("/employees");
        JsonPath jsonPath = response.jsonPath();
        List<String> list = jsonPath.getList("items.job_id");
        Set set = new HashSet(list);
        for (Object o : set) {
            Assert.assertTrue(o.toString().startsWith("SA"));
        }


    }
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}")
                .get("http://34.228.41.120:1000/ords/hr/countries");
        JsonPath jsonPath = response.jsonPath();
        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(jsonPath.getInt("count"),6);
        Assert.assertEquals(jsonPath.getString("hasMore"),"false");
        List<Object> list = jsonPath.getList("items.region_id");
        for (Object o : list) {
            Assert.assertEquals(o,3);
        }
        Assert.assertTrue(jsonPath.getList("items.country_name").containsAll(new ArrayList<String>(Arrays.asList("Australia","China","India","Japan","Malaysia","Singapore"))));

    }
}
