package br.com.inatel.recipeEvaluation.adapter;

import br.com.inatel.recipeEvaluation.exception.TastyConnectionException;

import br.com.inatel.recipeEvaluation.model.rest.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import springfox.documentation.annotations.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;


import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RecipeAdapter {
    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private String serverPort;

    @Value("${tasty.host}")
    private String tastyHost;

    @Value("${tasty.port}")
    private String tastyPort;

    private String tastyBaseUrl;

    private WebClient webClient;

    @Value("${X_RapidApi_Key}")
    private String key;

    /**
     * method executed after every dependency injection
     */
    @PostConstruct
    public void buildTastyBaseUrl(){
        this.tastyBaseUrl = String.format("https://%s/%s", this.tastyHost, this.tastyPort);
        this.webClient = WebClient.builder().baseUrl(this.tastyBaseUrl).build();
    }

    /**
     * I use Mono normally to consume non-reactive APIs (an already complete response block)
     * I use Flux when I'm consuming reactive APIs that return me the chunked response and when that happens if I'm using Mono
     * I just take the first block
     */
    @Cacheable(value = "recipe")
    public List<Results> getAllRecipe(String recipeEvaluation){
        try{
            Results recipesArr = this.webClient
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
            return Arrays.asList(recipesArr);
        }catch(WebClientException webClientException){
            throw new TastyConnectionException(this.tastyBaseUrl);
        }
    }

    @CacheEvict(value = "recipe")
    public void clearRecipeCache(){
        log.info("Cache cleared");
    }
}