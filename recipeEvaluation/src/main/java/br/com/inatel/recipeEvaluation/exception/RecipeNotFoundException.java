package br.com.inatel.recipeEvaluation.exception;

import br.com.inatel.recipeEvaluation.model.entity.RecipeEvaluation;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException (RecipeEvaluation recipeEvaluation){
        super(String.format("Recipe with recipeId = '%s' was not found.", recipeEvaluation.getRecipeId()));
    }
}
