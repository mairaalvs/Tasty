package br.com.inatel.recipeEvaluation.mapper;

import br.com.inatel.recipeEvaluation.model.dto.RecipeEvaluationDto;
import br.com.inatel.recipeEvaluation.model.entity.Evaluation;
import br.com.inatel.recipeEvaluation.model.entity.RecipeEvaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    /**
     * transform from dto list to entity list
     */
    public static List<RecipeEvaluation> toRecipeEvaluationList(List<RecipeEvaluationDto> recipeEvaluationDtoList){
        return recipeEvaluationDtoList.stream().map(recipeEvaluationDto -> toRecipeEvaluation(recipeEvaluationDto)).collect(Collectors.toList());
    }

    /**
     * transform from entity list to dto list
     */
    public static List<RecipeEvaluationDto> toRecipeEvaluationDtoList(List<RecipeEvaluation> recipeEvaluationList){
        return recipeEvaluationList.stream().map(recipeEvaluation -> toRecipeEvaluationDto(recipeEvaluation)).collect(Collectors.toList());
    }

    /**
     * transform from dto to entity
     */
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

    /**
     * entity to dto transform
     */
    public static RecipeEvaluationDto toRecipeEvaluationDto(RecipeEvaluation recipeEvaluation){

        RecipeEvaluationDto recipeEvaluationDto = RecipeEvaluationDto.builder()
                .id(recipeEvaluation.getId())
                .recipeId(recipeEvaluation.getRecipeId())
                .evaluations(new HashMap<>())
                .build();

        /**
         * I transform my rating into a key value with the date and the number of stars
         */
        recipeEvaluationDto.setEvaluations(recipeEvaluation.getEvaluations().stream()
                .collect(Collectors.toMap(Evaluation::getDate, Evaluation::getStars)));

        return recipeEvaluationDto;
    }
}
