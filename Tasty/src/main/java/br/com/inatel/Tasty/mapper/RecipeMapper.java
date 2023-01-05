package br.com.inatel.Tasty.mapper;

import br.com.inatel.Tasty.model.dto.RecipeEvaluationDto;
import br.com.inatel.Tasty.model.entity.Evaluation;
import br.com.inatel.Tasty.model.entity.RecipeEvaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {
    //transformo de lista dto pra lista de entidade
    public static List<RecipeEvaluation> toRecipeEvaluationList(List<RecipeEvaluationDto> recipeEvaluationDtoList){
        return recipeEvaluationDtoList.stream().map(recipeEvaluationDto -> toRecipeEvaluation(recipeEvaluationDto)).collect(Collectors.toList());
    }

    //transformo de lista de entidade pra lista dto
    public static List<RecipeEvaluationDto> toRecipeEvaluationDtoList(List<RecipeEvaluation> recipeEvaluationList){
        return recipeEvaluationList.stream().map(recipeEvaluation -> toRecipeEvaluationDto(recipeEvaluation)).collect(Collectors.toList());
    }


    //transformo de dto para entidade
    public static RecipeEvaluation toRecipeEvaluation(RecipeEvaluationDto recipeEvaluationDto){

        RecipeEvaluation recipeEvaluation = RecipeEvaluation.builder()
                .id(recipeEvaluationDto.getId())
                .recipeId(recipeEvaluationDto.getRecipeId())
                .evaluations(new ArrayList<>())
                .build();

        recipeEvaluationDto.getEvaluations().entrySet().stream().forEach(evaluationEntry -> recipeEvaluation.addEvaluation(Evaluation.builder()
                .date(evaluationEntry.getKey())
                .stars(evaluationEntry.getValue())
                .build()));

        return recipeEvaluation;
    }

    //transformo de entidade para dto
    public static RecipeEvaluationDto toRecipeEvaluationDto(RecipeEvaluation recipeEvaluation){

        RecipeEvaluationDto recipeEvaluationDto = RecipeEvaluationDto.builder()
                .id(recipeEvaluation.getId())
                .recipeId(recipeEvaluation.getRecipeId())
                .evaluations(new HashMap<>())
                .build();

        //transformo minha avaliação em chave valor com a data e o numero de estrelas
        recipeEvaluationDto.setEvaluations(recipeEvaluation.getEvaluations().stream()
                .collect(Collectors.toMap(Evaluation::getDate, Evaluation::getStars)));

        return recipeEvaluationDto;
    }
}
