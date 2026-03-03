package com.victor.stock;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductResourceTest {

    private String uniqueCode() {
        return "P" + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

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
        String code = uniqueCode();

        given()
                .contentType("application/json")
                .body(validProductJson(code))
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("code", is(code))
                .body("price", is(2500.0f));
    }

    @Test
    void shouldNotCreateProductWithDuplicateCode() {
        String code = uniqueCode();

        // Primeiro create
        given()
                .contentType("application/json")
                .body(validProductJson(code))
                .when()
                .post("/products")
                .then()
                .statusCode(201);

        // Segundo create com mesmo code
        given()
                .contentType("application/json")
                .body(validProductJson(code))
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
        String code = uniqueCode();

        given()
                .contentType("application/json")
                .body(validProductJson(code))
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
                .get("/products/999999")
                .then()
                .statusCode(404);
    }

    // =========================
    // UPDATE
    // =========================

    @Test
    void shouldUpdateProduct() {
        String code = uniqueCode();

        Long id = given()
                .contentType("application/json")
                .body(validProductJson(code))
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");

        String updateJson = """
            {
                "code": "%s",
                "name": "Notebook Gamer",
                "price": 3000.0
            }
        """.formatted(code);

        given()
                .contentType("application/json")
                .body(updateJson)
                .when()
                .put("/products/" + id)
                .then()
                .statusCode(200)
                .body("name", is("Notebook Gamer"))
                .body("price", is(3000.0f));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingProduct() {
        String code = uniqueCode();

        given()
                .contentType("application/json")
                .body(validProductJson(code))
                .when()
                .put("/products/999999")
                .then()
                .statusCode(404);
    }

    // =========================
    // DELETE
    // =========================

    @Test
    void shouldDeleteProduct() {
        String code = uniqueCode();

        Long id = given()
                .contentType("application/json")
                .body(validProductJson(code))
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
                .delete("/products/999999")
                .then()
                .statusCode(404);
    }
}