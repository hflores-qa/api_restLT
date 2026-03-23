package utils;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Nombre: LoggingFilter
 * Funcionamiento: Intercepta request y response de RestAssured para loguearlos con Log4j
 */
public class LoggingFilter implements Filter {

    private static final Logger log = LogManager.getLogger(LoggingFilter.class);

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        // ===== REQUEST =====
        log.info("========== REQUEST ==========");
        log.info("URI: {}", requestSpec.getURI());
        log.info("Method: {}", requestSpec.getMethod());
        log.info("Headers: {}", requestSpec.getHeaders());

        Object body = requestSpec.getBody();

        if (body != null) {
            log.info("Body: {}", String.valueOf(body));
        }

        // Ejecutar request
        Response response = ctx.next(requestSpec, responseSpec);

        // ===== RESPONSE =====
        log.info("========== RESPONSE ==========");
        log.info("Status Code: {}", response.getStatusCode());
        log.info("Headers: {}", response.getHeaders());
        log.info("Body: {}", response.getBody().asString());

        return response;
    }
}