Feature: World Ocean Database Extractor (WOD05)

    As a scientist
    I want to explore a quality-controlled ocean profile
    so that I can see measurements of the corresponding values
    
Scenario: Valid input data with position and depth in ocean
    Given the user is on WOD05 Extractor Page
    When he enters "21" as latitude
    And he enters "-67" as longitude
    And he enters "3000" as depth
    And he submits request by clicking the Calculate button
    Then ensure the result is complete
