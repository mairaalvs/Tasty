package br.com.inatel.Tasty.service;

import br.com.inatel.Tasty.adapter.RecipeAdapter;
import br.com.inatel.Tasty.exception.RecipeNotFoundException;
import br.com.inatel.Tasty.mapper.RecipeMapper;
import br.com.inatel.Tasty.model.dto.RecipeDto;
import br.com.inatel.Tasty.model.entity.RecipeEvaluation;
import br.com.inatel.Tasty.model.rest.Results;
import br.com.inatel.Tasty.repository.RecipeRepository;
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

    public List<RecipeDto> getAllRecipeEvaluation(){

        return RecipeMapper.toRecipeDtoList(recipeRepository.findAll());
    }

    public List<RecipeDto> getRecipeByRecipeId(String recipeId){
        return RecipeMapper.toRecipeDtoList(recipeRepository.findByRecipeId(recipeId));
    }

//    public List<RecipeDto> getEvaluationsByStars(int stars){
//        return RecipeMapper.toRecipeDtoList(recipeRepository.findByStars(stars));
//    }

    public RecipeDto saveEvaluation(RecipeDto recipeDto) {

        //transforma o DTO numa entidade de persistencia
        RecipeEvaluation recipeEvaluation = RecipeMapper.toRecipeEvaluation(recipeDto);

        if(isRecipeValid(recipeEvaluation)){
            //salvo no banco se for valido e transformo novamente para dto e retorno esse dto para o controller
            return RecipeMapper.toRecipeEvaluationDto(recipeRepository.save(recipeEvaluation));
        }
        throw new RecipeNotFoundException(recipeEvaluation);
    }

    private Boolean isRecipeValid(RecipeEvaluation recipeEvaluation){

        return recipeAdapter.getAllRecipe(recipeEvaluation.getRecipeId())
                .stream()
                .anyMatch(results -> results.getResults()[0].getDisplay()
                        .equals(recipeEvaluation.getRecipeId()));
    }

}