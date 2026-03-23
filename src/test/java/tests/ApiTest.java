package tests;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import utils.*;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Epic("API Tests")
@Feature("Usuarios")
@Tag("SMOKE")
public class ApiTest extends BaseTest {

    /**
     * Caso de prueba : Obtener lista de usuarios
     * Funcionamiento: Valida el endpoint /users y su schema
     * Precondiciones: Servicio disponible
     */
    @Test
    @Tag("USERS")
    public void testGetUsers() {

        StepLogger.step("Enviar request GET /users");

        Response response = given()
                .when()
                .get("/users");


        StepLogger.step("Validar status code 200");
        ApiValidator.statusCode(response, 200);

        StepLogger.step("Validar schema users.json");
        response.then().body(matchesJsonSchemaInClasspath("schema/users.json"));

        // Validación adicional (se mantiene)
        assertThat(response.asString(), matchesJsonSchemaInClasspath("schema/users.json"));

        List<Integer> ids = response.jsonPath().getList("id");
        StepLogger.debug("IDs obtenidos: " + ids);

        StepLogger.step("Validar que exista el usuario con id 1");
        ApiValidator.listContains(ids, 1);

        StepLogger.step("Validar lista no vacía");
        ApiValidator.listNotEmpty(ids);
    }

    /**
     * Caso de prueba : Obtener usuario por ID
     * Funcionamiento: Valida respuesta y adjunta evidencia completa
     * Precondiciones: Existe registro con id=1
     */
    @Test
    @Tag("USERS")
    public void testGetUserById() {

        StepLogger.step("Enviar request GET /posts/{id} con id=1");

        Response response =
                given()
                        .pathParam("id", 1)
                        .when()
                        .get("/posts/{id}");

        StepLogger.step("Validar status code 200");
        ApiValidator.statusCode(response, 200);

        StepLogger.step("Validar que userId sea igual a 1");
        response.then().body("userId", equalTo(1));

        String responseBody = response.getBody().asString();
        String headers = response.getHeaders().toString();

        StepLogger.debug("Response Body: " + responseBody);
        StepLogger.debug("Headers: " + headers);

        StepLogger.step("Adjuntando evidencia en Allure");

        AllureUtils.attachText("Endpoint", "/posts/{id}");
        AllureUtils.attachText("Método", "GET");
        AllureUtils.attachText("Status Code", String.valueOf(response.getStatusCode()));
        AllureUtils.attachText("Headers", headers);
        AllureUtils.attachResponse("Response Body", responseBody);
    }

    /**
     * Caso de prueba : Validar usuario por objeto
     * Funcionamiento: Extrae response y lo adjunta a Allure
     * Precondiciones: Endpoint disponible
     */
    @Test
    @Tag("POSTS")
    public void testGetUserByIdValidateByObject() {

        StepLogger.step("Enviar request GET /posts/{id}");

        String response =
                given()
                        .when()
                        .pathParam("id", 1)
                        .get("/posts/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        StepLogger.debug("Response extraído: " + response);

        StepLogger.step("Adjuntar body de respuesta en Allure");
        AllureUtils.attachResponse("Body extraído", response);
    }

    /**
     * Caso de prueba : Crear post
     * Funcionamiento: Valida creación de recurso
     * Precondiciones: Endpoint disponible
     */
    @Test
    @Tag("POSTS")
    @Disabled("Actualmente presenta error")
    public void testCreatePost() {

        StepLogger.step("Construir request body");

        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", 1);

        StepLogger.debug("Request Body: " + requestBody.toString());

        StepLogger.step("Enviar request POST /posts");

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody.toString())
                        .when()
                        .post("/posts");

        StepLogger.step("Validar status code 201");
        ApiValidator.statusCode(response, 201);

        StepLogger.step("Validar que el title sea 'foo'");
        response.then().body("title", equalTo("foo"));

        StepLogger.step("Adjuntar request y response en Allure");

        AllureUtils.attachResponse("Request Body", requestBody.toString());
        AllureUtils.attachResponse("Response Body", response.getBody().asString());
    }
}