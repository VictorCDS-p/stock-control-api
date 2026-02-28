package com.victor.stock;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductResourceTest {

    private String validProductJson(String code) {
        return """
            {
                "code": "%s",
                "name": "Notebook",
                "price": 2500.0
            }
        """.formatted(code);
    }

    // =========================
    // CREATE
    // =========================

    @Test
    void shouldCreateProduct() {
        given()
                .contentType("application/json")
                .body(validProductJson("P001"))
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("code", is("P001"))
                .body("price", is(2500.0f));
    }

    @Test
    void shouldNotCreateProductWithDuplicateCode() {

        given()
                .contentType("application/json")
                .body(validProductJson("P002"))
                .when()
                .post("/products")
                .then()
                .statusCode(201);

        given()
                .contentType("application/json")
                .body(validProductJson("P002"))
                .when()
                .post("/products")
                .then()
                .statusCode(409);
    }

    @Test
    void shouldReturn400WhenCreatingWithInvalidData() {

        String invalidBody = """
            {
                "code": "",
                "name": "",
                "price": -10
            }
        """;

        given()
                .contentType("application/json")
                .body(invalidBody)
                .when()
                .post("/products")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    // =========================
    // GET
    // =========================

    @Test
    void shouldListProducts() {

        given()
                .contentType("application/json")
                .body(validProductJson("P003"))
                .when()
                .post("/products")
                .then()
                .statusCode(201);

        given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThan(0));
    }

    @Test
    void shouldReturn404WhenProductNotFound() {

        given()
                .when()
                .get("/products/999")
                .then()
                .statusCode(404);
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateProduct() {

        Long id =
                given()
                        .contentType("application/json")
                        .body(validProductJson("P004"))
                        .when()
                        .post("/products")
                        .then()
                        .statusCode(201)
                        .extract()
                        .jsonPath()
                        .getLong("id");

        String updateJson = """
            {
                "code": "P004",
                "name": "Notebook Gamer",
                "price": 3000.0
            }
        """;

        given()
                .contentType("application/json")
                .body(updateJson)
                .when()
                .put("/products/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingProduct() {

        given()
                .contentType("application/json")
                .body(validProductJson("P999"))
                .when()
                .put("/products/999")
                .then()
                .statusCode(404);
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteProduct() {

        Long id =
                given()
                        .contentType("application/json")
                        .body(validProductJson("P004"))
                        .when()
                        .post("/products")
                        .then()
                        .statusCode(201)
                        .extract()
                        .jsonPath()
                        .getLong("id");

        given()
                .when()
                .delete("/products/" + id)
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistingProduct() {

        given()
                .when()
                .delete("/products/999")
                .then()
                .statusCode(404);
    }
}