package br.com.inatel.Tasty.service;

import br.com.inatel.Tasty.adapter.RecipeAdapter;
import br.com.inatel.Tasty.exception.RecipeNotFoundException;
import br.com.inatel.Tasty.mapper.RecipeMapper;
import br.com.inatel.Tasty.model.dto.RecipeEvaluationDto;
import br.com.inatel.Tasty.model.entity.RecipeEvaluation;
import br.com.inatel.Tasty.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@Service
@Slf4j
public class RecipeService {

    @Autowired
    private RecipeAdapter recipeAdapter;

    @Autowired
    RecipeRepository recipeRepository;

    public List<RecipeEvaluationDto> getAllRecipeEvaluation(){

        return RecipeMapper.toRecipeEvaluationDtoList(recipeRepository.findAll());
    }

    public List<RecipeEvaluationDto> getRecipeByRecipeId(String recipeId){
        return RecipeMapper.toRecipeEvaluationDtoList(recipeRepository.findByRecipeId(recipeId));
    }

    @Cacheable(value = "recipe")
    public RecipeEvaluationDto saveEvaluation(RecipeEvaluationDto recipeEvaluationDto) {

        //transforma o DTO numa entidade de persistencia
        RecipeEvaluation recipeEvaluation = RecipeMapper.toRecipeEvaluation(recipeEvaluationDto);

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