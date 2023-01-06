Feature: Testing API

Background: 
    * def url_base = 'http://localhost:8081/evaluation'

Scenario: Testing the return of the getAll method
    Given url url_base
    When method get
    Then status 200

Scenario: Testing the return of the getAll method with an invalid structure
    Given url url_base
    And path '/123'
    When method get
    Then status 400    

Scenario: Testing return method getByRecipeId and checking the Json
    Given url url_base
    And path '/recipe?id=cakes'
    When method get
    Then status 200

    * def first = response[0].id

    And print first
    And match first == 1
    
Scenario: Testing return method getByRecipeId with RecipeId invalid
    Given url url_base
    And path '/recipe?id=invalid'
    When method get
    Then status 200
    
    * def first = response

    And print first
    And match first == []

Scenario: Testing creating an evaluation of a recipe with valid recipeId
    * def evaluation =
      """
      {
        "recipeId": "chicken pasta",
        "evaluations":{
            "2023-07-15": 3
        }
      }
      """

    Given url url_base
    And request evaluation
    When method post
    Then status 201

    * def first = response.recipeId

    And print first
    And match first == "chicken pasta"

Scenario: Testing creating an evaluation of a recipe with invalid recipeId
    * def evaluation =
      """
      {
        "recipeId": "invalid",
        "evaluations":{
            "2023-07-15": 3
        }
      }
      """

    Given url url_base
    And request evaluation
    When method post
    Then status 500

Scenario: Testing creating an evaluation of a recipe with invalid date format (1)
    * def evaluation =
      """
      {
        "recipeId": "lemon cake",
        "evaluations":{
            "2023/07/15": 3
        }
      }
      """

    Given url url_base
    And request evaluation
    When method post
    Then status 400

    * def first = response.httpStatusCode

    And print first
    And match first == "BAD_REQUEST"

Scenario: Testing creating an evaluation of a recipe with invalid date format (2)
    * def evaluation =
      """
      {
        "recipeId": "lemon cake",
        "evaluations":{
            "06-01-2023": 3
        }
      }
      """

    Given url url_base
    And request evaluation
    When method post
    Then status 400

    * def first = response.httpStatusCode

    And print first
    And match first == "BAD_REQUEST"
    
Scenario: Testing creating an evaluation of a recipe with invalid date format (3)
    * def evaluation =
      """
      {
        "recipeId": "lemon cake",
        "evaluations":{
            "06/01/2023": 3
        }
      }
      """

    Given url url_base
    And request evaluation
    When method post
    Then status 400

    * def first = response.httpStatusCode

    And print first
    And match first == "BAD_REQUEST"

Scenario: Testing cache clearing
    Given url url_base
    And path 'cache'
    When method delete
    Then status 204      