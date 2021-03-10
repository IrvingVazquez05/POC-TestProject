@PetsStore 
Feature: Title of your feature
  I want to use this template for my feature file

  @GetPetById
  Scenario: Get Pet By ID
    Given a pet exists with an petId of 4565
    When I send a request to get petService
    Then response status code is 200
    And response contains the pet name "Rocko"  

