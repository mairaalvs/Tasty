package br.com.inatel.Tasty.controller;

import br.com.inatel.Tasty.adapter.RecipeAdapter;
import br.com.inatel.Tasty.model.dto.RecipeEvaluationDto;
import br.com.inatel.Tasty.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluation")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeAdapter recipeAdapter;

    @GetMapping
    public ResponseEntity<List<RecipeEvaluationDto>> getEvaluations(@RequestParam(required = false) Optional<String> recipeId) {
        return ResponseEntity.ok().body(recipeService.getAllRecipeEvaluation());
    }

    @GetMapping("/{recipe}")
    public ResponseEntity<List<RecipeEvaluationDto>> getEvaluation(@RequestParam("id") @PathVariable String recipeId) {
        return ResponseEntity.ok().body(recipeService.getRecipeByRecipeId(recipeId));
    }

    @PostMapping
    public ResponseEntity<RecipeEvaluationDto> addEvaluation(@RequestBody @Valid RecipeEvaluationDto recipeEvaluationDto) {
        return ResponseEntity.created(null).body(recipeService.saveEvaluation(recipeEvaluationDto));
    }

    @DeleteMapping("/evaluationcache")
    public ResponseEntity<?> deleteRecipeCache() {
        recipeAdapter.clearRecipeCache();
        return ResponseEntity.noContent().build();
    }
}