# 🧪 **api_restLT**

**Autor:** Hugo Villasana

Proyecto de pruebas automatizadas de API con **RestAssured**, **JUnit 5** y **Allure 2.24**.
Permite ejecutar tests por **ambiente**, **suite** o **tag**, y generar reportes visuales.

---

# 📑 **ÍNDICE**

* 📌 Descripción
* ⚙️ Requisitos
* 📂 Estructura
* 📦 Dependencias
* 🚀 Ejecución
* 📊 Reportes Allure
* 🏷️ Tags
* ⚠️ Notas

---

# 📝 **DESCRIPCIÓN**

Proyecto enfocado en automatización de APIs.

### ✔️ Permite:

* Validar endpoints y respuestas
* Ejecutar por ambiente, suite o tag
* Generar reportes visuales con evidencia (request/response)
* Clasificación de errores con Allure

---

# ⚙️ **REQUISITOS**

* ☕ Java 17
* 📦 Maven 3.8+
* 🌐 API disponible
* 📊 Allure CLI instalado

---

# 📂 **ESTRUCTURA**

TestJAVA/
├─ src/test/java/...          # Tests
├─ src/test/resources/...     # JSON, schemas, config
├─ pom.xml

---

# 📦 **DEPENDENCIAS**

* REST Assured 5.4.0
* JSON Schema Validator 5.4.0
* JSON 20231013
* JUnit 5.10.2
* Allure 2.24.0
* SLF4J / Log4j

---

# 🚀 **EJECUCIÓN DE TESTS**

## ▶️ 1. Ejecutar + generar reporte automático

mvn clean test; allure generate target/allure-results --clean -o target/allure-report

---

## 🌎 2. Ejecutar por ambiente

QA (default)
mvn clean test

DEV
mvn clean test -Denv=DEV

PROD
mvn clean test -Denv=PROD

---

## 🧪 3. Ejecutar por clase / suite

Una clase
mvn clean test -Dtest=SmokeTest

Varias clases
mvn clean test -Dtest=SmokeTest,RegressionTest

---

## 🏷️ 4. Ejecutar por TAG

🔹 Ejecutar un tag específico:
mvn clean test "-Dgroups=USER"

🔹 Ejecutar múltiples tags (OR):
mvn clean test "-Dgroups=SMOKE,USER"

🔹 Excluir tags:
mvn clean test "-DexcludedGroups=USER"

🔹 Ejecutar SMOKE excluyendo USER:
mvn clean test "-Dgroups=SMOKE" "-DexcludedGroups=USER"

💡 Tip: Respeta mayúsculas/minúsculas en @Tag

---

# 📊 **REPORTE ALLURE**

Generar reporte:
allure generate target/allure-results -o target/allure-report --clean

Levantar servidor:
allure serve target/allure-results

Nota:
El archivo categories.json se copia automáticamente desde:
src/test/resources/allure-setup

hacia:
target/allure-results

---

# 🏷️ **EJEMPLO DE TAGS**

@Epic("API Tests")
@Feature("Usuarios")

@Test
@Tag("SMOKE")
public void testGetUsers() { }

@Test
@Tag("USER")
public void testGetUserById() { }

@Test
@Tag("SMOKE")
@Tag("USER")
public void testMixto() { }

---

# ⚠️ **NOTAS IMPORTANTES**

* Todos los tests deben tener @Test
* @Tag sin @Test NO ejecuta
* Maven usa 👉 -Dgroups (no junit.jupiter.tags)
* -Dgroups funciona como OR
* Usa -DexcludedGroups para excluir tests
* Evita mezclar demasiados tags sin estrategia
* Configuración de Allure ya incluida
* Schemas deben ir en:
  src/test/resources/schema/

---

# 🎯 **RESUMEN RÁPIDO**

| Acción         | Comando                              |
| -------------- | ------------------------------------ |
| Ejecutar todo  | mvn clean test                       |
| Ejecutar USER  | -Dgroups=USER                        |
| Ejecutar SMOKE | -Dgroups=SMOKE                       |
| SMOKE sin USER | -Dgroups=SMOKE -DexcludedGroups=USER |
| Reporte        | allure serve target/allure-results   |

---

🔥 Proyecto listo para escalar a nivel **framework profesional**
