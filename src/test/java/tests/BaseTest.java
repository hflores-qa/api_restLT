package tests;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
//import utils.ConfigReader;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.ConfigReaderUtils;

public class BaseTest {
    @ExtendWith(AllureJunit5.class)
    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = ConfigReaderUtils.get("base.url");
    }

}