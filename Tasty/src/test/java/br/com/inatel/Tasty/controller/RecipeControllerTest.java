package br.com.inatel.Tasty.controller;

import br.com.inatel.Tasty.model.dto.RecipeEvaluationDto;
import br.com.inatel.Tasty.model.entity.RecipeEvaluation;
import com.intuit.karate.Json;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Order;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeControllerTest {

    @Test
    @Order(1)
    void givenAReadOrder_WhenReceivingAllTheRecipes_ThenItShouldReturnStatus200Ok() {
        WebTestClient.bindToServer().baseUrl("http://localhost:8081").build()
                .get()
                .uri("/evaluation")
                .exchange()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectStatus().isOk();
    }

    @Test
    @Order(2)
    void givenAReadOrderByRecipeIdValid_WhenReceivingTheEvaluation_ThenItShouldReturnStatus200Ok() {
        String recipeId = "chocolate cake";

        RecipeEvaluationDto[] recipeEvaluationDto = WebTestClient.bindToServer().baseUrl("http://localhost:8081/evaluation").build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipe")
                        .queryParam("id", recipeId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(RecipeEvaluationDto[].class)
                .returnResult()
                .getResponseBody();

        assertNotNull(recipeEvaluationDto);
        assertEquals(recipeEvaluationDto[0].getRecipeId(),recipeId);
    }

    @Test
    @Order(3)
    void givenAReadOrderByRecipeIdInvalid_WhenReceivingTheEvaluation_ThenItShouldReturnStatus200Ok() {
        String recipeId = "invalid";

        RecipeEvaluationDto[] recipeEvaluationDto = WebTestClient.bindToServer().baseUrl("http://localhost:8081/evaluation").build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipe")
                        .queryParam("id", recipeId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(RecipeEvaluationDto[].class)
                .returnResult()
                .getResponseBody();

        assertEquals(recipeEvaluationDto.length, 0);
    }

}