Feature: Edit data 

    As an administrator of the OSD-Registry
    I want edit the stored participant and sampling site data
    so that I can make modifications and corrections if it is necessary
    
Scenario: Modify data  
    Given the administrator is on osd_admin_edit page "http://www.megx.net/bla/bla"
    When he submits modified data by clicking the save button
    Then ensure the modified data were stored by displaying the message "Changes saved!"
    
