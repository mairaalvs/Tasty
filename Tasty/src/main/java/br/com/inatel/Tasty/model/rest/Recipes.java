package br.com.inatel.Tasty.model.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipes {

    private String display;

    private String search_value;

    private String type;
}
