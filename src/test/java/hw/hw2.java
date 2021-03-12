package hw;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class hw2 {
    static {baseURI="http://34.228.41.120:8000/api";}

    @Test
    public void test1(){
        given().accept(ContentType.JSON)
                .pathParam("id","20")
                .when().get("/spartans/{id}")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().header("Date",notNullValue())
                .and().header("Transfer-Encoding",equalTo("chunked"))
                .and().body("id",equalTo(20),"name",equalTo("Lothario"),
                "gender",equalTo("Male"),"phone",equalTo(7551551687L));
    }

    @Test
    public void test2(){
        given().accept(ContentType.JSON)
                .queryParam("nameContains","r")
                .queryParam("gender","Female")
                .when().get("/spartans/search")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("content.name".toLowerCase(),everyItem(containsStringIgnoringCase("r")))
                .and().body("size",equalTo(20))
                .and().body("totalPages",equalTo(1))
                .and().body("sort.sorted",equalTo(false));
    }

}
