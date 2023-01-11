package br.com.inatel.recipeEvaluation.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RecipeEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String recipeId;

    @OneToMany(mappedBy = "recipeEvaluation", fetch=FetchType.EAGER)
    private List<Evaluation> evaluations = new ArrayList<>();

    public void addEvaluation(Evaluation evaluation){
        evaluations.add(evaluation);
        evaluation.setRecipeEvaluation(this);
    }

}
