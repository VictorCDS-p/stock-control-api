package com.victor.stock;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class RawMaterialResourceTest {

    private String validRawMaterialJson(String code) {
        return """
            {
                "code": "%s",
                "name": "Steel",
                "stockQuantity": 100
            }
        """.formatted(code);
    }

    // =========================
    // CREATE
    // =========================

    @Test
    void shouldCreateRawMaterial() {
        given()
                .contentType("application/json")
                .body(validRawMaterialJson("RM001"))
                .when()
                .post("/raw-materials")
                .then()
                .statusCode(201)
                .body("code", is("RM001"))
                .body("stockQuantity", is(100));
    }

    // =========================
    // GET
    // =========================

    @Test
    void shouldListRawMaterials() {

        given()
                .contentType("application/json")
                .body(validRawMaterialJson("RM002"))
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
                .get("/raw-materials/999")
                .then()
                .statusCode(404);
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateRawMaterial() {

        Long id =
                given()
                        .contentType("application/json")
                        .body(validRawMaterialJson("RM003"))
                        .when()
                        .post("/raw-materials")
                        .then()
                        .statusCode(201)
                        .extract()
                        .jsonPath()
                        .getLong("id");

        String updateJson = """
            {
                "code": "RM003",
                "name": "Aluminum",
                "stockQuantity": 200
            }
        """;

        given()
                .contentType("application/json")
                .body(updateJson)
                .when()
                .put("/raw-materials/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingRawMaterial() {

        given()
                .contentType("application/json")
                .body(validRawMaterialJson("RM999"))
                .when()
                .put("/raw-materials/999")
                .then()
                .statusCode(404);
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteRawMaterial() {

        Long id =
                given()
                        .contentType("application/json")
                        .body(validRawMaterialJson("RM004"))
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
                .delete("/raw-materials/999")
                .then()
                .statusCode(404);
    }
}