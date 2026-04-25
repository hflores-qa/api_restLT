package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import utils.ConfigReaderUtils;
import io.qameta.allure.restassured.AllureRestAssured;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Nombre de la clase: BaseTest
 * Funcionamiento: Clase base para la configuración global de pruebas API
 * Incluye inicialización de RestAssured, configuración de baseURI,
 * integración con Allure y habilitación de logs automáticos
 */
public class BaseTest {

    // Logger de Log4j para registrar eventos durante la ejecución de pruebas
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    /**
     * Nombre del metodo: setup
     * Funcionamiento: Inicializa la configuración base de las pruebas API.
     * - Establece la URL base desde archivo de configuración
     * - Configura logs de ejecución con Log4j
     * - Activa filtros de RestAssured para logging automático
     * - Integra Allure para evidencias de request/response
     */
    @BeforeAll
    public static void setup() {

        // Obtiene la URL base desde archivo de configuración
        RestAssured.baseURI = ConfigReaderUtils.get("base.url");

        // Logs informativos de inicialización
        log.info("======================================");
        log.info("Inicializando pruebas API");
        log.info("Base URI: {}", RestAssured.baseURI);
        log.info("======================================");

        // Configuración de filtros:
        // - AllureRestAssured: adjunta request y response al reporte Allure
        // - RequestLoggingFilter: muestra request en consola/logs
        // - ResponseLoggingFilter: muestra response en consola/logs
        RestAssured.filters(
                new AllureRestAssured(),
                new RequestLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }
}