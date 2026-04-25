package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
//import utils.ConfigReader;
import utils.ConfigReaderUtils;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ConfigReaderUtils.get("base.url");
    }

}