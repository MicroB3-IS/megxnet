Feature: Delete data 

    As an administrator of the OSD-Registry
    I want delete stored participant and sampling site data
    so that I can remove a participant or a sampling site
    
Scenario: Delete all data from one datarecord
    Given the administrator is on osd_admin_delete page "<tail-of-url>"
    When he select one datarecord     
    And he clicks the ok button from an appearing confirmation panel
    Then the datarecord was removed
    
