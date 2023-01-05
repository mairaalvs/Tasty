package br.com.inatel.Tasty.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeEvaluationDto {

    private Long id;

    @NotNull
    private String recipeId;

    @NotNull
    private Map<LocalDate, Integer> evaluations;
}