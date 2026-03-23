package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigReaderUtils;
import io.qameta.allure.restassured.AllureRestAssured;

public class BaseTest {

    /**
     * Inicializa la configuración base de RestAssured,
     * establece la URL base y agrega el filtro de Allure
     */
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ConfigReaderUtils.get("base.url");

        // Filtro automático para Allure (request + response completos)
        RestAssured.filters(new AllureRestAssured());
    }
}