Feature: Verify Mandatory Data 

    As an administrator of the OSD-Registry,
    I want ensure to have all valid mandatory data,
    so that I can already start registration procedure even in absence of important other data fields
    
Scenario Outline: Mandatory Fields

    Given the admin want ensure to have complete mandatory data on page "http://www.megx.net/bla/bla"   
    When the entered contact name is "<contact_name>" 
    And the entered email is "<contact_email>"  
    And the entered sampling site name is "<sampling_site_name>"    
    Then he should receive a message with "<status>"   
    
    Examples:
    | contact_name | contact_email | sampling_site_name | status     |
    | Anne         | ak@osd.de     | Bremen             | complete   |
    |              |               |                    | incomplete |
    | Julia        |               | Neustadt           | incomplete |
    
Scenario Outline: Verification of email

    Given an email input field on page "http://www.megx.net/bla/bla"
    When the email input is "<contact_email>" 
    Then the email is "<validation_status>"
    
    Examples:
        
    | contact_email | validation_status |
    | ak@osd.de     | valid             |
    | dfss.com      | invalid           |
    
Scenario Outline: Verification of Contact Name   
 
    Given contact name input fields on page "http://www.megx.net/bla/bla"
    When the contact first name is "<contact_first_name>" 
    And the contact last name is "<contact_last_name>"
    Then the contact name is "<validation_status>"
    
    Examples:
    
    | contact_first_name | contact_last_name | validation_status |
    | 1234               |                   | invalid           |
    | Anne               | Kolhoff           | valid             |
    | Julia              |                   | valid             |
    |                    | Schnetzer         | valid             |
    |                    |                   | invalid           |
    
    
  
    
    