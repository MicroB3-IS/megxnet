Feature: Verify Optional Data

   As an administrator of the OSD-Registry,
   I want ensure to have valid optional data,
   so that I can ensure to have data correctness

Scenario: Optional Fields existing

    Given the admin want input optional data on page "http://www.megx.net/bla/bla" 
    When all optional data fields are visible 
    
    | optional_fields        |
    | institute              |
    | institute_lat          |
    | institute_lon          |
    | institute_country      |
    | institute_url          |
    | sampling_site_lat      |
    | sampling_site_lon      |
    | sampling_site_id       |
    | sampling_site_descript |
  
    Then he can enter and submit optional data
    
Scenario Outline: Verification of decimal values latitude and longitude

    Given lat long fields on page "http://www.megx.net/bla/bla"
    When the lat long input for an according field is "<decimal_value>"
    Then the decimal values are "<validation_status>"
    
    Examples:
        
    | decimal_value | validation_status |
    |   54.456234   | valid             |
    |    8.345677   | valid             |
    |    8.345      | invalid           |
    |     .3456     | invalid           |
    | 2234.345678   | invalid           |
    |  45.45dfg     | invalid           |   
    |  45,458967    | invalid           |      
   








 