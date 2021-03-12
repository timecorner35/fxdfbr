package hw;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class hw1 {
    static {baseURI="http://34.228.41.120:1000/ords/hr";}
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
        given().accept(ContentType.JSON)
                .and().queryParam("q","{\"department_id\":80}")
                .when().get("/employees")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("items.job_id",everyItem(startsWith("SA")))
                .and().body("items.department_id",everyItem(equalTo(80)))
                .and().body("count",equalTo(25));

    }

    @Test
    public void test3(){
        given().accept(ContentType.JSON)
                .and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries")
                .then().statusCode(200)
                .and().body("items.region_id",everyItem(equalTo(3)))
                .and().body("count",equalTo(6))
                .and().body("hasMore",equalTo(false))
                .and().body("items.country_name",hasItems("Australia","China","India","Japan","Malaysia","Singapore"));

    }
}
