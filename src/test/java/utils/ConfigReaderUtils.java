package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReaderUtils {

    private static Properties properties = new Properties();

    static {
        try {
            // Obtenemos el ambiente desde variable de sistema "env", default = "QA"
            String env = System.getProperty("env", "QA");
            String fileName = env + ".properties";

            // Cambiamos la ruta para buscar dentro de resources/config/
            InputStream input = ConfigReaderUtils.class
                    .getClassLoader()
                    .getResourceAsStream("config/" + fileName);

            if (input == null) {
                throw new RuntimeException("Archivo de configuración no encontrado: config/" + fileName);
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando archivo de configuración: " + e.getMessage(), e);
        }
    }

    /** Obtiene el valor de la clave desde el archivo de configuración */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}