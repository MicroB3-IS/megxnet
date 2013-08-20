Feature: Data Management

    As an administrator of the OSD-Registry
    I want store manage participant data 
    so that I can prepare the information for a sampling-site   
    
Scenario: The administrator want m participant
    Given the administrator is on OSD-Registry Page "<tail-of-url>"
    When he submits data by clicking the save button
    Then ensure the data were stored by displaying the message "Saving successful!"