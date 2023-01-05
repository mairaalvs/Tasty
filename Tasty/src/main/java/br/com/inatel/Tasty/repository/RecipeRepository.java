package br.com.inatel.Tasty.repository;

import br.com.inatel.Tasty.model.entity.RecipeEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEvaluation, String> {
    List<RecipeEvaluation> findByRecipeId(String recipeId);

//    List<RecipeEvaluation> findByStars(int stars);
}
