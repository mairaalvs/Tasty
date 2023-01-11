package br.com.inatel.recipeEvaluation.adapter;

import br.com.inatel.recipeEvaluation.exception.TastyConnectionException;
import br.com.inatel.recipeEvaluation.model.rest.Results;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class RecipeAdapterTest {
    private static final String key = "719578a6camshdf67fc4b7488967p1d4a00jsn05a4da13e021";

    @Test
    public void getAllRecipe() {
        String recipeEvaluation = "chocolate cake";
        String tastyBaseUrl = "https://tasty.p.rapidapi.com/recipes";
        String tastyHost = "tasty.p.rapidapi.com";

        try{
            Results recipesArr = WebClient.create(tastyBaseUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/auto-complete")
                            .queryParam("prefix", recipeEvaluation)
                            .build())
                    .header("X-RapidAPI-Key", key)
                    .header("X-RapidAPI-Host", tastyHost)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Results.class)
                    .block();

            assertNotNull(recipesArr);
        }catch(WebClientException webClientException){
            System.out.println(webClientException);
            throw new TastyConnectionException(tastyBaseUrl);
        }
    }
}
