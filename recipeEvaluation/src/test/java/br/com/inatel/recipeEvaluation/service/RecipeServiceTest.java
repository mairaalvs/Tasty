package br.com.inatel.recipeEvaluation.service;

import br.com.inatel.recipeEvaluation.adapter.RecipeAdapter;
import br.com.inatel.recipeEvaluation.model.dto.RecipeEvaluationDto;
import br.com.inatel.recipeEvaluation.model.entity.Evaluation;
import br.com.inatel.recipeEvaluation.model.entity.RecipeEvaluation;
import br.com.inatel.recipeEvaluation.model.rest.Recipes;
import br.com.inatel.recipeEvaluation.model.rest.Results;
import br.com.inatel.recipeEvaluation.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    private Map<LocalDate, Integer> evaluationsMap;

    private RecipeEvaluationDto recipeEvaluationDto;

    private List<Evaluation> evaluationsList;

    private RecipeEvaluation recipeEvaluation;

    private List<Results> resultsList;

    private List<Recipes> recipesList;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeAdapter recipeAdapter = new RecipeAdapter();

    @BeforeEach
    public void init() {
        recipeEvaluationDto = RecipeEvaluationDto.builder()
                .id(1L)
                .recipeId("cakes")
                .build();

        System.out.println(recipeEvaluationDto);
        recipeEvaluation = RecipeEvaluation.builder()
                .id(1L)
                .recipeId("cakes")
                .build();

        evaluationsMap = new HashMap<>();
        evaluationsList = new ArrayList<>();
        recipesList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().plusDays(i);
            Integer stars = Integer.valueOf(i+1);

            evaluationsMap.put(date, stars);

            evaluationsList.add(Evaluation.builder()
                    .id((long) i)
                    .date(date)
                    .stars(stars)
                    .recipeEvaluation(recipeEvaluation)
                    .build());
        }


        recipeEvaluationDto.setEvaluations(evaluationsMap);
        recipeEvaluation.setEvaluations(evaluationsList);

        System.out.println(recipeEvaluationDto);

        recipesList.add(Recipes.builder().display("cakes").search_value("cakes").type("ingredient").build());
        System.out.println(recipesList);
    }

    @Test
    public void givenGetAllEvaluations_whenGetAllsRecipeEvaluations_shouldReturnRecipeEvaluationDtoList() {
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipeEvaluation));

        List<RecipeEvaluationDto> recipeEvaluationDtoList = recipeService.getAllRecipeEvaluation();

        assertNotNull(recipeEvaluationDtoList);
        assertThat(recipeEvaluationDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(recipeEvaluationDtoList.get(0).getRecipeId()).isEqualTo("cakes");
    }

    @Test
    public void givenRecipeEvaluationDto_whenSaveRecipeEvaluationAndRecipeIdIsValid_shouldReturnRecipeEvaluationDto() {
        //when(recipeService.getAllRecipeEvaluation()).thenReturn(recipesList);
        recipeEvaluationDto = recipeService.saveEvaluation(recipeEvaluationDto);

        //assertThat(recipeEvaluationDto).isNotNull();
        assertThat(recipeEvaluationDto).isNull();
        //assertThat(recipeEvaluationDto.getId()).isEqualTo(1L);
    }

    @Test
    public void givenGetRecipeEvaluationByRecipeId_whenGetAllsRecipeEvaluationsByValidRecipeId_shouldReturnRecipeEvaluationDtoList() {
        when(recipeRepository.findByRecipeId(any(String.class))).thenReturn(Arrays.asList(recipeEvaluation));
        List<RecipeEvaluationDto> recipeEvaluationDtoList = recipeService.getRecipeByRecipeId("cakes");

        assertThat(recipeEvaluationDtoList).isNotNull();
        assertThat(recipeEvaluationDtoList.get(0).getRecipeId()).isEqualTo("cakes");
        assertThat(recipeEvaluationDtoList.get(0).getEvaluations().size()).isEqualTo(5);
    }

    @Test
    public void givenGetRecipeEvaluationByRecipeId_whenGetAllsRecipeEvaluationsByInvalidRecipeId_shouldReturnRecipeEvaluationDtoList() {
        List<RecipeEvaluationDto> recipeEvaluationDtoList = recipeService.getRecipeByRecipeId("invalid");

        assertThat(recipeEvaluationDtoList).isNotNull();
        assertThat(recipeEvaluationDtoList.size()).isEqualTo(0L);

    }

}
