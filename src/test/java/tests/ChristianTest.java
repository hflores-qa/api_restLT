package tests;

import Model.Posts;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;

//ejecuta el TestSuite
//mvn clean test

//Ejecuta testsuite por tag
//mvn clean test -Denv="USER"

//Ejecuta testsuite y realiza index offline de reporte
//mvn clean test; allure generate target/allure-results --clean -o allure-report

//Realiza servidor de allure para visualizar reporte
//allure serve target/allure-results

@Epic("API Tests")              // Agrupa todas las pruebas en un "Epic"
@Feature("Usuarios")            // Define la funcionalidad principal
@Tag("API")                     //mvn test -Dgroups=API
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChristianTest extends BaseTest {

    @Test
    @Order(1)
    @Tag("USER")
    @Story("Obtener usuario por ID")
    @Description("Validar que el endpoint /posts/{id} devuelve el usuario correcto")
    public void testGetUserById() {
        given()
                .when()
                .pathParam("id",1)
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("userId",equalTo(1))
                .log();
        Allure.step("Validación de userId=1 completada");
    }

    @Order(3)
    @Story("Obtener lista de usuarios")
    @Description("Validar que el endpoint /users devuelve la lista correctamente")
    public void testGetUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schema/users.json"))
                .log();
        Allure.step("Validación de esquema JSON de usuarios completada");
    }

    @Test
    @Order(2)
    @Tag("USER")
    @Story("Obtener usuario por ID")
    @Description("Validar que el endpoint /posts/{id} devuelve el usuario correcto")
    public void testGetUserByIdOrig() {
        given()
                .when()
                .pathParam("id",1)
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("userId",equalTo(1))
                .log();
        Allure.step("Validación de userId=1 completada");
    }

    @Test
    @Order(1)
    @Tag("USER")
    @Story("Validar usuario por objeto")
    @Description("Extraer respuesta como objeto Posts y validar")
    public void testGetUserByIdValidateByObject() {
        Posts post = given()
                .when()
                .pathParam("id",1)
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .extract()
                .as(Posts.class);
        Allure.step("Objeto Posts extraído: " + post.getTitle());
    }

    @Test
    @Story("Crear un nuevo post")
    @Description("Validar que el endpoint /posts crea un recurso correctamente")
    public void testCreatePost() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", 1);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(1))
                .log();
        Allure.step("Post creado con título 'foo'");
    }
}