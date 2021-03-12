package homework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Hw2 {
    @Test
    public void test1(){
        given().accept(ContentType.JSON)
                .and().pathParam("id","20")
                .when().get("http://34.228.41.120:8000/api/spartans/{id}")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().header("Date",notNullValue())
                .and().header("Transfer-Encoding",equalTo("chunked"))
                .and().body("id",equalTo(20),
                                "gender",equalTo("Male"),
                                "phone",equalTo(7551551687L),
                                "name",equalTo("Lothario"));

    }

    @Test
    public void test2(){
        given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","r")
                .when().get("http://34.228.41.120:8000/api/spartans/search")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("totalPages",equalTo(1))
                .and().body("size",equalTo(20))
                .and().body("sort.sorted",equalTo(false));
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","r")
                .when().get("http://34.228.41.120:8000/api/spartans/search");
        List<String> path = response.path("content.name");
        for (String s : path) {
            Assert.assertTrue(s.contains("r"));
        }


    }

}
