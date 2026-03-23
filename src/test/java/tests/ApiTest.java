package tests;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import utils.AllureUtils;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testGetUsers() {

        // Paso: enviar request
        Allure.step("Enviar request GET /users");

        Response response = given()
                .when()
                .get("/users");

        // Paso: validar status code
        Allure.step("Validar status code 200");
        response.then().statusCode(200);

        // Paso: validar schema
        Allure.step("Validar schema users.json");
        response.then().body(matchesJsonSchemaInClasspath("schema/users.json"));

        // Validación adicional con assert
        assertThat(response.asString(), matchesJsonSchemaInClasspath("schema/users.json"));

        // Extraer lista de IDs
        List<Integer> ids = response.jsonPath().getList("id");

        // Paso: validar existencia de usuario
        Allure.step("Validar que exista el usuario con id 1");
        assertTrue(ids.contains(1), "El usuario con id 1 debería existir");

        // Paso: validar lista no vacía
        Allure.step("Validar que la lista de usuarios no esté vacía");
        assertFalse(ids.isEmpty(), "Debería haber al menos un usuario");
    }

    /**
     * Caso de prueba : Obtener usuario por ID
     * Funcionamiento: Valida respuesta y adjunta evidencia completa
     * Precondiciones: Existe registro con id=1
     */
    @Tag("USER")
    public void testGetUserById() {

        // Paso: enviar request con path param
        Allure.step("Enviar request GET /posts/{id} con id=1");

        Response response =
                given()
                        .pathParam("id", 1)
                        .when()
                        .get("/posts/{id}");

        // Paso: validar status code
        Allure.step("Validar status code 200");
        response.then().statusCode(200);

        // Paso: validar contenido
        Allure.step("Validar que userId sea igual a 1");
        response.then().body("userId", equalTo(1));

        // Extraer datos para evidencia
        String responseBody = response.getBody().asString();
        String headers = response.getHeaders().toString();
        int statusCode = response.getStatusCode();

        // Paso: adjuntar evidencia en Allure
        Allure.step("Adjuntando evidencia completa");

        AllureUtils.attachText("Endpoint", "/posts/{id}");
        AllureUtils.attachText("Método", "GET");
        AllureUtils.attachText("Status Code", String.valueOf(statusCode));
        AllureUtils.attachText("Headers", headers);
        AllureUtils.attachResponse("Response Body", responseBody);
    }

    /**
     * Caso de prueba : Validar usuario por objeto
     * Funcionamiento: Extrae response y lo adjunta a Allure
     * Precondiciones: Endpoint disponible
     */
    @Tag("USER")
    public void testGetUserByIdValidateByObject() {

        // Paso: enviar request y extraer response
        Allure.step("Enviar request GET /posts/{id} y extraer body");

        String response =
                given()
                        .when()
                        .pathParam("id", 1)
                        .get("/posts/{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .asString();

        // Paso: adjuntar response
        Allure.step("Adjuntar body de respuesta en Allure");
        AllureUtils.attachResponse("Body extraído", response);
    }

    /**
     * Caso de prueba : Crear post
     * Funcionamiento: Valida creación de recurso
     * Precondiciones: Endpoint disponible
     */
    public void testCreatePost() {

        // Paso: construir request body
        Allure.step("Construir request body para creación de post");

        JSONObject requestBody = new JSONObject();
        requestBody.put("title", "foo");
        requestBody.put("body", "bar");
        requestBody.put("userId", 1);

        // Paso: enviar request POST
        Allure.step("Enviar request POST /posts");

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(requestBody.toString())
                        .when()
                        .post("/posts");

        // Paso: validar status code
        Allure.step("Validar status code 201");
        response.then().statusCode(201);

        // Paso: validar contenido
        Allure.step("Validar que el title sea 'foo'");
        response.then().body("title", equalTo("foo"));

        // Adjuntar evidencia
        Allure.step("Adjuntar request y response en Allure");
        AllureUtils.attachResponse("Request Body", requestBody.toString());
        AllureUtils.attachResponse("Response Body", response.getBody().asString());
    }
}