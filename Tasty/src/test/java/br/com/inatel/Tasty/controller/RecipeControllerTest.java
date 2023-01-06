package br.com.inatel.Tasty.controller;

import br.com.inatel.Tasty.model.dto.RecipeEvaluationDto;
import br.com.inatel.Tasty.model.entity.RecipeEvaluation;
import com.intuit.karate.Json;
import com.intuit.karate.JsonUtils;
import org.json.JSONArray;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Order;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        String recipeId = "cakes";

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

    @Test
    @Order(4)
    void givenACleaningOrder_ThenItShouldReturnStatus204NoContent() {
        WebTestClient.bindToServer().baseUrl("http://localhost:8081/evaluation").build()
                .delete()
                .uri("/cache")
                .exchange()
                .expectStatus().isNoContent();
    }
}