package br.com.inatel.recipeEvaluation.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipes {

    private String display;

    private String search_value;

    private String type;
}
