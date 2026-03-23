package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import io.qameta.allure.*;
import utils.AllureUtils;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

@Epic("API Tests")
@Feature("Usuarios")
@Tag("API")
public class StephanyTest extends BaseTest {

    /**
     * Caso de prueba : Validar User=4 param catchPhrase
     * Funcionamiento: Valida respuesta
     * Precondiciones: Existe registro con id=4
     */
    @Tag("SMOKE")
    @Story("Obtener lista de usuarios")
    @Description("Validar que el endpoint /users devuelve en el id=4 catchPhrase: Multi-tiered zero tolerance productivity")
    public void testValidarCatchPhraseUser4() {
        given()
                .log().all()
                .pathParam("id", 4)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                //.body(matchesJsonSchemaInClasspath("schema/users.json")
                .body("company.catchPhrase", equalTo("Multi-tiered zero tolerance productivity"));

        Allure.step("Validación correcta del campo catchPhrase");
    }

    /**
     * Caso de prueba : Validar User=4 param catchPhrase
     * Funcionamiento: Valida respuesta
     * Precondiciones: Existe registro con id=4
     */
    @Tag("SMOKE")
    @Story("Obtener lista de usuarios")
    @Description("Validar que el endpoint /users devuelve en el id=4 catchPhrase: Multi-tiered zero tolerance productivity")
    public void testValidarCatchPhraseUser4_2() {
        Response response =
                given()
                        .pathParam("id", 4)
                        .when()
                        .get("/users/{id}");
        response.then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("company.catchPhrase", equalTo("Multi-tiered zero tolerance productivity"));

        String responseBody = response.getBody().asString();
        String headers = response.getHeaders().toString();
        int statusCode = response.getStatusCode();

        Allure.step("Adjuntando evidencia adicional");

        AllureUtils.attachText("Endpoint", "/users/{id}");
        AllureUtils.attachText("Método", "GET");
        AllureUtils.attachText("Status Code", String.valueOf(statusCode));
        AllureUtils.attachText("Headers", headers);
        AllureUtils.attachResponse("Response Body", responseBody);
    }
}