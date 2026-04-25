package utils;

import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

public class ApiValidator {

    /**
     * Nombre del metodo: statusCode
     * Funcionamiento: Valida el código de respuesta HTTP
     */
    public static void statusCode(Response response, int expected) {
        assertEquals(expected, response.getStatusCode(),
                "El status code no es el esperado");
    }

    /**
     * Nombre del metodo: bodyFieldEquals
     * Funcionamiento: Valida que un campo del body sea igual al esperado
     */
    public static void bodyFieldEquals(Response response, String key, Object expected) {
        assertEquals(expected, response.jsonPath().get(key),
                "El valor del campo no coincide");
    }

    /**
     * Nombre del metodo: listNotEmpty
     * Funcionamiento: Valida que una lista no esté vacía
     */
    public static void listNotEmpty(java.util.List<?> list) {
        assertFalse(list.isEmpty(), "La lista no debería estar vacía");
    }

    /**
     * Nombre del metodo: listContains
     * Funcionamiento: Valida que una lista contenga un valor
     */
    public static void listContains(java.util.List<?> list, Object value) {
        assertTrue(list.contains(value), "El valor no existe en la lista");
    }
}