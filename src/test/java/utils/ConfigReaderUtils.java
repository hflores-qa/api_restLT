package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReaderUtils {

    private static Properties properties = new Properties();

    static {
        try {
            String env = System.getProperty("env", "qa").toLowerCase();
            String fileName = env + ".properties";

            // Carga desde classpath (resources/config/)
            try (InputStream input = ConfigReaderUtils.class
                    .getClassLoader()
                    .getResourceAsStream("config/" + fileName)) {

                if (input == null) {
                    throw new RuntimeException(
                            "No se encontró el archivo: config/" + fileName +
                                    " en src/test/resources/config/"
                    );
                }

                properties.load(input);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error cargando configuración: " + e.getMessage(), e
            );
        }
    }

    // Obtener valor por key
    public static String get(String key) {
        return properties.getProperty(key);
    }
}