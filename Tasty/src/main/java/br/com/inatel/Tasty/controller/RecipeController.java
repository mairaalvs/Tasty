package br.com.inatel.Tasty.controller;

import br.com.inatel.Tasty.model.dto.RecipeDto;
import br.com.inatel.Tasty.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @GetMapping
//    public ResponseEntity<List<RecipeDto>> getEvaluations(@RequestParam(required = false) Optional<String> recipeId) {
//
//        List<RecipeDto> recipelist;
//        if (recipeId.isPresent()){
//            recipelist = recipeService.getRecipeByRecipeId(recipeId.get());
//        }
//        else {
//            recipelist = recipeService.getAllRecipeEvaluation();
//        }
//        return ResponseEntity.ok(recipelist);
//    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getEvaluations(@RequestParam(required = false) Optional<String> recipeId) {
        return ResponseEntity.ok().body(recipeService.getAllRecipeEvaluation());
    }

    @GetMapping("/{recipe}")
    public ResponseEntity<List<RecipeDto>> getEvaluation(@RequestParam("id") @PathVariable String recipeId) {
        return ResponseEntity.ok().body(recipeService.getRecipeByRecipeId(recipeId));
    }

//    @GetMapping("/")
//    public ResponseEntity<List<RecipeDto>> getStars(@RequestParam("stars") @PathVariable int stars) {
//        return ResponseEntity.ok().body(recipeService.getEvaluationsByStars(stars));
//    }

    @PostMapping
    public ResponseEntity<RecipeDto> addEvaluation(@RequestBody @Valid RecipeDto recipeDto) {
        return ResponseEntity.created(null).body(recipeService.saveEvaluation(recipeDto));
    }
}
