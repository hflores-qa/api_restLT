package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;

@Epic("API Tests")
@Feature("Validación de Ubicación de Usuarios")
@Tag("API")
public class MariaETest extends BaseTest {

    /**
     * Caso de prueba: Validar latitud y esquema del usuario 2
     * Funcionamiento: Valida el endpoint /users/{id}, el esquema JSON y la precisión del campo latitud.
     * Precondiciones: Servicio disponible y existencia del archivo schema/latSchema.json
     */
    @Tag("SMOKE")
    @Story("Validar latitud del usuario 2")
    @Description("Valida esquema, nodo asignado, longitud y contenido de la latitud para el ID 2")
    public void testValidarLatitudUsuario2() {

        Response response = given()
                .log().all()
                .pathParam("id", 2)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .extract().response();

        // Extrae latitud asegurando formato String usando .toString()
        // A veces response.path devuelve un objeto genérico y por eso no reconoce .length()
        String latitud = response.path("address.geo.lat").toString();
        Allure.step("Nodo latitud extraído: " + latitud);

        // 1. Validar Contenido
        assertEquals("-43.9509", latitud, "El contenido de la latitud no es el esperado");
        Allure.step("Contenido de latitud validado correctamente");

        // 2. Validar Longitud
        int longitudEsperada = 8;
        assertEquals(longitudEsperada, latitud.length(), "La longitud del campo lat no coincide");
        Allure.step("Longitud del campo validada: " + latitud.length() + " caracteres");

        // 3. Validar Esquema JSON
        Allure.step("Validación de JSON Schema contra latSchema.json");
        //response.then().body(matchesJsonSchemaInClasspath("schema/latSchema.json"));
        assertThat(response.asString(),matchesJsonSchemaInClasspath("schema/latSchema.json"));


        response.then().log().body();
    }
}