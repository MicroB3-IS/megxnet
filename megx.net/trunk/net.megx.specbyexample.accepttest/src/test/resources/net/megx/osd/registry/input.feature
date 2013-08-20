Feature: Input Data 

    As an administrator of the OSD-Registry
    I want input participant data in the registration-system
    so that I can store these data    
    
Scenario: The administrator want input data from a new participant
    Given the administrator is on OSD-Registry Page "<tail-of-url>"
    When he submits data by clicking the save button
    Then ensure the data were stored by displaying the message "Saving successful!"