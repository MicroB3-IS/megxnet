Feature: Verify Publishable Sampling Sites

  As an OSD administrator,
  I want to only publish sampling sites which have a minimum data correct and present,
  so that only high quality data gets visible to the public   
  
Scenario: Availablity of minimum data for a new Sampling Site

    Given the admin wants check minimum data available on page "http://www.megx.net/bla/bla" 
    When a minimum of data is available
    
    | minimum_data_for_sampling_site |
    | institute                      |   
    | institute_country              |   
    | sampling_site_lat              |
    | sampling_site_lon              |
    | sampling_site_id               |  
  
    Then the admin gets a message with 
    """
    The minimum Sampling Site data is complete. Now you can publish 
    a new Sampling Site.
    """
    
 
  
  
  