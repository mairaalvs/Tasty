package br.com.inatel.recipeEvaluation.service;

import br.com.inatel.recipeEvaluation.adapter.RecipeAdapter;
import br.com.inatel.recipeEvaluation.exception.RecipeNotFoundException;
import br.com.inatel.recipeEvaluation.mapper.RecipeMapper;
import br.com.inatel.recipeEvaluation.model.dto.RecipeEvaluationDto;
import br.com.inatel.recipeEvaluation.model.entity.RecipeEvaluation;
import br.com.inatel.recipeEvaluation.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecipeService {

    @Autowired
    private RecipeAdapter recipeAdapter;

    @Autowired
    RecipeRepository recipeRepository;

    public List<RecipeEvaluationDto> getAllRecipeEvaluation(){

        List<RecipeEvaluation> recipeEvaluationList = recipeRepository.findAll();
        recipeEvaluationList.forEach(r -> r.getEvaluations().size());
        return RecipeMapper.toRecipeEvaluationDtoList(recipeEvaluationList);
    }

    public List<RecipeEvaluationDto> getRecipeByRecipeId(String recipeId){
        return RecipeMapper.toRecipeEvaluationDtoList(recipeRepository.findByRecipeId(recipeId));
    }

    public RecipeEvaluationDto saveEvaluation(RecipeEvaluationDto recipeEvaluationDto) {

         //turns the DTO into a persistence entity
        RecipeEvaluation recipeEvaluation = RecipeMapper.toRecipeEvaluation(recipeEvaluationDto);

        if(isRecipeValid(recipeEvaluation)){
            //saved in the database if it is valid and I transform it again to dto and return this dto to the controller
            return RecipeMapper.toRecipeEvaluationDto(recipeRepository.save(recipeEvaluation));
        }
        throw new RecipeNotFoundException(recipeEvaluation);
    }

    public Boolean isRecipeValid(RecipeEvaluation recipeEvaluation){

        return recipeAdapter.getAllRecipe(recipeEvaluation.getRecipeId())
                .stream()
                .anyMatch(results -> results.getResults()[0].getDisplay()
                        .equals(recipeEvaluation.getRecipeId()));
//        List<Results> results = recipeAdapter.getAllRecipe(recipeEvaluation.getRecipeId());
//        log.warn(results.toString());
//        if(results.size() == 0)
//            return false;
//        return true;
    }

}