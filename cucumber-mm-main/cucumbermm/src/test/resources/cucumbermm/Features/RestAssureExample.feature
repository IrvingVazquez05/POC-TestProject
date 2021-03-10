@restassure
Feature: Rest Assure example

  Scenario: I should be able to get a response 
    Given the services are running
    When I send a get request to endpoint "/baeldung"
    Then response status code should be 200
    And response is "Welcome to Baeldung!"    