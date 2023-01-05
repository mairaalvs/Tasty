package br.com.inatel.Tasty.teste;

import br.com.inatel.Tasty.model.rest.Recipes;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ex {
    private static final String API_KEY = "719578a6camshdf67fc4b7488967p1d4a00jsn05a4da13e021";

    @Test
    public void getAllRecipe() {
        String recipeEvaluation = "soup";
        String tastyBaseUrl = "https://tasty.p.rapidapi.com/recipes/auto-complete";
        try {
            Mono<Recipes[]> mono = WebClient.create(tastyBaseUrl)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("prefix", recipeEvaluation)
                            .build())
                    .header("X_RapidAPI_Key", API_KEY)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Recipes[].class);

            Recipes[] recipesArr = mono.block();

            assertNotNull(recipesArr);

        } catch (WebClientException e) {
            e.printStackTrace();
            fail( e.getMessage() );
        }
    }
}
