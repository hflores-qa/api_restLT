package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReaderUtils {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReaderUtils.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando config.properties");
        }
    }

    /**
     * Nombre del metodo: get
     * Funcionamiento: Obtiene valor desde config.properties
     * Parametros: key (String)
     * Return: String
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}