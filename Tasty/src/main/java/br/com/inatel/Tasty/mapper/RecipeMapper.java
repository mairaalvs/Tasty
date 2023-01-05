package br.com.inatel.Tasty.mapper;

import br.com.inatel.Tasty.model.dto.RecipeDto;
import br.com.inatel.Tasty.model.entity.Evaluation;
import br.com.inatel.Tasty.model.entity.RecipeEvaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {
    //transformo de lista dto pra lista de entidade
    public static List<RecipeEvaluation> toRecipeList(List<RecipeDto> recipeDtoList){
        return recipeDtoList.stream().map(recipeDto -> toRecipeEvaluation(recipeDto)).collect(Collectors.toList());
    }

    //transformo de lista de entidade pra lista dto
    public static List<RecipeDto> toRecipeDtoList(List<RecipeEvaluation> recipeList){
        return recipeList.stream().map(recipe -> toRecipeEvaluationDto(recipe)).collect(Collectors.toList());
    }


    //transformo de dto para entidade
    public static RecipeEvaluation toRecipeEvaluation(RecipeDto recipeDto){

        RecipeEvaluation recipe = RecipeEvaluation.builder()
                .id(recipeDto.getId())
                .recipeId(recipeDto.getRecipeId())
                .evaluations(new ArrayList<>())
                .build();

        recipeDto.getEvaluations().entrySet().stream().forEach(evaluationEntry -> recipe.addEvaluation(Evaluation.builder()
                .date(evaluationEntry.getKey())
                .stars(evaluationEntry.getValue())
                .build()));

        return recipe;
    }

    //transformo de entidade para dto
    public static RecipeDto toRecipeEvaluationDto(RecipeEvaluation recipeEvaluation){

        RecipeDto recipeDto = RecipeDto.builder()
                .id(recipeEvaluation.getId())
                .recipeId(recipeEvaluation.getRecipeId())
                .evaluations(new HashMap<>())
                .build();

        //transformo minha avaliação em chave valor com a data e o numero de estrelas
        recipeDto.setEvaluations(recipeEvaluation.getEvaluations().stream()
                .collect(Collectors.toMap(Evaluation::getDate, Evaluation::getStars)));

        return recipeDto;
    }
}
