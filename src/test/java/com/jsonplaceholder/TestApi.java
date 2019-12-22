package com.jsonplaceholder;


import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static resources.BaseConfig.*;


import static org.hamcrest.Matchers.equalTo;
public class TestApi {
    @BeforeMethod
    static public void setup() {
        baseURI = baseurl;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        requestSpecification = given().
                header("Accept-Language", "RU,ru;q=0.8,en- US;q=0.5,en;q=0.3").
                header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 YaBrowser/19.12.2.252 Yowser/2.5 Safari/537.36").
                header("Connection", "keep-alive");
    }

    @Test
    public void test1() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .extract()
                .response();
    }

    @Test
    public void test2() {
        JsonPath jsonPath = given()
                .param("userId", 2)
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();
        Assert.assertEquals("eveniet quod temporibus", jsonPath.get("title[4]"));
    }

    @Test
    public void test3() {
        JsonPath jsonPath = given()
                .param("postId", 5)
                .get("/comments")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();
        Assert.assertEquals("Sophia@arianna.co.uk", jsonPath.get("email[2]"));
    }

    @Test
    public void test4() {
        JsonPath jsonPath = given()
                .param("userId", 5)
                .get("/albums")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath();
        Assert.assertEquals("sed qui sed quas sit ducimus dolor", jsonPath.get("title[9]"));
    }

    @Test
    public void test5() {
        String body = "{\n" +
                "   \"postId\":3,\n" +
                "   \"id\":31,\n" +
                "   \"name\":\"Ivan\",\n" +
                "   \"email\":\"Ivan@mail..ru\",\n" +
                "   \"body\":\"Its a test\"\n" +
                "}";
        given().body(body).when().post("/posts").then().statusCode(201);

    }

    @Test
    public void test6() {
        String body = "{\n" +
                "   \"postId\":3,\n" +
                "   \"id\":31,\n" +
                "   \"name\":\"Ivan\",\n" +
                "   \"email\":\"Ivan@mail..ru\",\n" +
                "   \"body\":\"Its a test new\"\n" +
                "}";
        given().body(body).when().put("/posts/3").then().statusCode(200);

    }

    @Test
    public void test7() {
        String body = "{\n" +
                "   \"userId\":6,\n" +
                "   \"id\":6,\n" +
                "   \"title\":\"any title\",\n" +
                "}";
        given().body(body).when().put("/albums/6").then().statusCode(200);

    }

    @Test
    public void test8() {
        String body = "{\n" +
                "   \"albumId\":2,\n" +
                "   \"id\":27,\n" +
                "   \"title\":\"anytitle\",\n" +
                "   \"url\":\"https://via.placeholder.com/anyurl\",\n" +
                "   \"thumbnailUrl\":\"https://via.placeholder.com/anyurl\"\n" +
                "}";
        given().body(body).when().put("/photos/72").then().statusCode(200);

    }

    @Test
    public void test9() {
        given().when().delete("/posts/10").then().statusCode(200);
    }

    @Test
    public void test10() {
        given().when().delete("/todos/12").then().statusCode(200);
    }

}