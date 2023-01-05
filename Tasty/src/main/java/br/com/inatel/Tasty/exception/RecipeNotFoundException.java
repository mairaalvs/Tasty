package br.com.inatel.Tasty.exception;

import br.com.inatel.Tasty.model.entity.RecipeEvaluation;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException (RecipeEvaluation recipeEvaluation){
        super(String.format("Recipe with recipeId = '%s' was not found.", recipeEvaluation.getRecipeId()));
    }
}
