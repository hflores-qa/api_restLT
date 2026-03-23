package tests;

import static org.hamcrest.core.IsEqual.equalTo;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;

//ejecuta el TestSuite
//mvn clean test

//Ejecuta testsuite por tag
//mvn clean test -Denv="USER"

//Ejecuta testsuite y realiza index offline de reporte
//mvn clean test; allure generate target/allure-results --clean -o allure-report

//Realiza servidor de allure para visualizar reporte
//allure serve target/allure-results

@Epic("API Tests")
@Feature("Usuarios")
@Tag("API")
public class ChristianTest extends BaseTest {

    @Tag("User")
    @Tag("SMOKE")
    @Story("Validar street de usuario 1")
    @Description("Obtener usuario 1 y validar que el street del address sea Kulas Light")
    public void testCompareUserStreetWithKulasById() {
        Response response =given()
                .when()
                .pathParam("id",1)
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .log().ifValidationFails()
                .extract().response();
        Allure.step("Validación de street de users/id=1 completada");
    }
}