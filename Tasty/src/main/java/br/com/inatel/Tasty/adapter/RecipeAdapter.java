package br.com.inatel.Tasty.adapter;

import br.com.inatel.Tasty.exception.TastyConnectionException;

import br.com.inatel.Tasty.model.rest.Notification;
import br.com.inatel.Tasty.model.rest.Results;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.reactive.function.BodyInserters;
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


    //método executado depois de toda injeção de dependencias
    @PostConstruct
    public void buildTastyBaseUrl(){
        this.tastyBaseUrl = String.format("https://%s/%s", this.tastyHost, this.tastyPort);
        this.webClient = WebClient.builder().baseUrl(this.tastyBaseUrl).build();
    }


    //uso o Mono normalmente para cnosumir APIs não reativas (um bloco de resposta ja completo)
    //uso o Flux quando estou consumindo APIs reativas que me retornam a resposta fragmentada e quando isso acontecesse se eu estou usando Mono
    //eu só pego o primeiro bloco

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

            System.out.println(recipesArr);
            return Arrays.asList(recipesArr);
        }catch(WebClientException webClientException){
            throw new TastyConnectionException(this.tastyBaseUrl);
        }
    }

    @CacheEvict(value = "recipe")
    public void clearRecipeCache(){
        log.info("Cache cleared");
    }

//    public Notification[] registerOnRecipeEvaluation(){
//    try{
//        //log.info("Registering at {}", this.tastyBaseUrl);
//        Notification notification = Notification.builder()
//                .host(this.serverHost)
//                .port(this.serverPort)
//                .build();
//
//        return this.webClient.post()
//                .uri("/notification")
//                .body(BodyInserters.fromValue(notification))
//                .retrieve()
//                .bodyToMono(Notification[].class)
//                .block();
//    } catch (WebClientException webClientException){
//        webClientException.printStackTrace();
//        throw new TastyConnectionException(this.tastyBaseUrl);
//    }
//}
}
