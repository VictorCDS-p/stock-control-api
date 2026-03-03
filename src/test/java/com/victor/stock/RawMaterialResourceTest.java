package com.victor.stock;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class RawMaterialResourceTest {

    private String validRawMaterialJson(String code) {
        return """
            {
                "code": "%s",
                "name": "Aço Inox",
                "stockQuantity": 100
            }
        """.formatted(code);
    }

    // =========================
    // CREATE
    // =========================

    @Test
    void shouldCreateRawMaterial() {
        String code = "R-" + UUID.randomUUID();
        given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(201)
                .body("code", is(code))
                .body("stockQuantity", is(100));
    }

    @Test
    void shouldNotCreateRawMaterialWithDuplicateCode() {
        String code = "R-" + UUID.randomUUID();

        // cria o primeiro
        given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(201);

        // tenta criar duplicado
        given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(409);
    }

    @Test
    void shouldReturn400WhenCreatingWithInvalidData() {
        String invalidJson = """
            {
                "code": "",
                "name": "",
                "stockQuantity": -5
            }
        """;

        given()
                .contentType("application/json")
                .body(invalidJson)
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(400);
    }

    // =========================
    // GET
    // =========================

    @Test
    void shouldListRawMaterials() {
        String code = "R-" + UUID.randomUUID();

        given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/raw-materials")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0));
    }

    @Test
    void shouldReturn404WhenRawMaterialNotFound() {
        given()
                .when()
                .get("/raw-materials/999999")
                .then()
                .statusCode(404);
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateRawMaterial() {
        String code = "R-" + UUID.randomUUID();

        Long id = given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");

        String updateJson = """
            {
                "code": "%s",
                "name": "Aço Galvanizado",
                "stockQuantity": 200
            }
        """.formatted(code);

        given()
                .contentType("application/json")
                .body(updateJson)
                .when()
                .put("/raw-materials/" + id)
                .then()
                .statusCode(200)
                .body("name", is("Aço Galvanizado"))
                .body("stockQuantity", is(200));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingRawMaterial() {
        String code = "R-" + UUID.randomUUID();

        given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .put("/raw-materials/999999")
                .then()
                .statusCode(404);
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteRawMaterial() {
        String code = "R-" + UUID.randomUUID();

        Long id = given()
                .contentType("application/json")
                .body(validRawMaterialJson(code))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");

        given()
                .when()
                .delete("/raw-materials/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingRawMaterial() {
        given()
                .when()
                .delete("/raw-materials/999999")
                .then()
                .statusCode(404);
    }
}