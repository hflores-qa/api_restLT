========================================
api_restLT
Autor: Hugo Villasana
========================================

Proyecto de pruebas automatizadas de API con RestAssured, JUnit 5 y Allure para la generación de reportes.
Permite ejecutar tests por ambiente, suite o tag, y generar reportes visuales con Allure.

----------------------------------------
Ejecución de Tests
----------------------------------------

1. Generar reportes Allure automáticamente
   mvn clean test; allure generate target/allure-results --clean -o target/allure-report

2. Ejecutar por ambiente

    - Por defecto carga QA:
      mvn clean test

    - Para DEV:
      mvn clean test -Denv=DEV

    - Para PROD:
      mvn clean test -Denv=PROD

3. Ejecutar por suite o clase

    - Ejecutar una suite específica:
      mvn clean test -Dtest=SmokeTest

    - Ejecutar varias suites separadas por coma:
      mvn clean test -Dtest=SmokeTest,RegressionTest

4. Ejecutar por tag

    - Ejemplo para tag SandraTest:
      mvn clean test -DincludeTags=SandraTest

    - También puedes combinar múltiples tags separados por coma:
      mvn clean test -DincludeTags=smoke,regression

----------------------------------------
Generación de Reporte Allure
----------------------------------------

Después de ejecutar los tests, genera y abre el reporte:

allure generate target/allure-results -o target/allure-report --clean
allure serve target/allure-results

Nota: El archivo categories.json se copia automáticamente desde
src/test/resources/allure-setup a target/allure-results.