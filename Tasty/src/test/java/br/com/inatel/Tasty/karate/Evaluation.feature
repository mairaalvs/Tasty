Feature: Testing Recipe Evaluation API

Scenario: get all recipes evaluated
        Given url 'http://localhost:8081/evaluation'
        When method get
        Then status 200