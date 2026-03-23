package utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StepLogger {

    private static final Logger log = LogManager.getLogger(StepLogger.class);

    /**
     * Nombre del metodo: step
     * Funcionamiento: Registra un paso en Allure y en logs al mismo tiempo
     * Parametros: message (String)
     * Return: No retorna valor
     */
    public static void step(String message) {
        log.info(message);
        Allure.step(message);
    }

    /**
     * Nombre del metodo: info
     * Funcionamiento: Log informativo
     */
    public static void info(String message) {
        log.info(message);
    }

    /**
     * Nombre del metodo: debug
     * Funcionamiento: Log de depuración
     */
    public static void debug(String message) {
        log.debug(message);
    }

    /**
     * Nombre del metodo: error
     * Funcionamiento: Log de error + paso en Allure
     */
    public static void error(String message) {
        log.error(message);
        Allure.step("ERROR: " + message);
    }
}