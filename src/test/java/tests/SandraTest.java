package tests;

import org.junit.jupiter.api.*;
import io.qameta.allure.*;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import io.restassured.response.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SandraTest extends BaseTest {

    /**
     * Caso de prueba: Validar nodo 9 de la lista de usuarios cargada en la clase ApiTest
     * Funcionamiento: Valida el json schema para el contenido del nodo 9
     * Precondiciones: Matriz de usuarios y json schema disponible
     */

    @Test
    @Story("Validar estructura del usuario 9")
    @Description("Valida que la lista de usuarios cumpla el schema y que el nodo 9 sea válido")
    public void testValidarNodo9ConSchema() {

        Response response = given()
                .log().all()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                // ✅ Valida TODO el array contra el schema
                .body(matchesJsonSchemaInClasspath("schema/users.json"))
                .extract().response();

        // ✅ Extraer nodo 9 (índice 8)
        int userId = response.path("[8].id");
        String username = response.path("[8].username");

        System.out.println("userID     " + userId);
        System.out.println("username     " + username);

        Allure.step("Nodo 9 extraído correctamente");

        // ✅ Validaciones específicas del nodo 9
        assertEquals(9, userId, "El id del nodo 9 no es correcto");

        Allure.step("El id del nodo 9 es correcto");

        assertEquals("Delphine", username, "El username del usuario 9 no es el esperado");
        Allure.step("Username del nodo 9 validado correctamente");

        response.then().log().body();
    }
}
