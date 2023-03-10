package br.com.inatel.recipeEvaluation.handler;

import br.com.inatel.recipeEvaluation.exception.RecipeNotFoundException;
import br.com.inatel.recipeEvaluation.exception.TastyConnectionException;
import br.com.inatel.recipeEvaluation.model.rest.Error;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error recipeNotFoundException(RecipeNotFoundException recipeNotFoundException){

        return Error.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .message(recipeNotFoundException.getMessage())
                .build();
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Error recipeArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){

        return Error.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .message("The recipe you searched for was not found.")
                .build();
    }

    @ExceptionHandler(TastyConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error tastyConnectionException(TastyConnectionException tastyConnectionException){

        return Error.builder()
                .httpStatusCode(HttpStatus.SERVICE_UNAVAILABLE)
                .message(tastyConnectionException.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error invalidFormatException(InvalidFormatException invalidFormatException){

        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(invalidFormatException.getMessage())
                .build();
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error jsonMappingException(JsonMappingException jsonMappingException){

        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(jsonMappingException.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Error methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

        return Error.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(methodArgumentNotValidException.getMessage())
                .build();
    }

    @ExceptionHandler(JDBCConnectionException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public Error jDBCConnectionException(JDBCConnectionException jDBCConnectionException){

        return Error.builder()
                .httpStatusCode(HttpStatus.SERVICE_UNAVAILABLE)
                .message(jDBCConnectionException.getMessage())
                .build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Error nullPointerException(NullPointerException nullPointerException){

        return Error.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(nullPointerException.getMessage())
                .build();
    }
}