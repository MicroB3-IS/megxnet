Feature: Verify Mandatory Data 

    As an administrator of the OSD-Registry,
    I want ensure to have all valid mandatory data,
    so that I can already start registration procedure even in absence of important other data fields
    
Scenario Outline: Mandatory Fields
    Given the administrator is on osd-registry input page
    When he submits input form
    Then <contact_name>
    And <contact_email>
    And <sampling_site_name> must be present 
    
    Examples:
    |contact_name|contact_email|sampling_site_name|valid|
    |Anne|ak@osd.de|Bremen|y|
    ||||n|
    |Julia||Neustadt|n|
    


Scenario Outline: Verification of email
    Given an email input field 
    When he inputs <contact_email> 
    Then email is <valid>
    
    Examples:
    
    |contact_email|valid|
    |ak@osd.de|y|
    |dfss.com|n|
    
Scenario Outline: Verification of Contact Name
    
    
    
    Examples:
    
    |contact_first_name|contact_last_name|valid|
    |6565||n|
    |Anne|Kolhoff|y|
    |Julia||y|
    ||Schnetzer|y|
    |||n|
    
    