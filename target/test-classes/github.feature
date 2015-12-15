Feature: Search Parkwayz on Goog;e

  Scenario: Open Google
    Given Google Homepage is diaplayed
    When When I entered Parkwayz in search box
    And I click on search Button
    Then Resultpage should be diplayed
    
