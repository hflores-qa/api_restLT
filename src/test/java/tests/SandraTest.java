package tests;

public class SandraTest extends BaseTest {
    public void test1() {

@Test
    @Order(2)
    @Tag("USER")
    @Story("Obtener usuario por ID")
    @Description("Validar que el endpoint /posts/{id} devuelve el usuario correcto")
    public void testGetUserById() {
        given()
                .when()
                .pathParam("id",1)
                .get("/posts/{id}")
                .then()
                .statusCode(200)
                .body("userId",equalTo(1))
                .log();
        Allure.step("Validación de userId=1 completada");
    }

        
    }
}
