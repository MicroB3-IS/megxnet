package net.megx.osd.registry;

import org.openqa.selenium.WebDriver;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VerifyMandatoryDataStepDefs 
{
    protected WebDriver driver;
	
	@Before
	public void setUp() 
	{
		//driver = new FirefoxDriver();
	}
	
	@Given("the admin want ensure to have complete mandatory data on page \"([^\"]*)\"") 
	public void ensure_mandatory_data(String url) throws Throwable
	{
	    //driver.get("http://www.megx.net/bla/bla);		
		throw new PendingException();
	}	
	
	// Scenario Outline: Mandatory Fields
	
	@When("the entered contact name is \"([^\"]*)\"")
	public void entered_contact_name(String name) throws Throwable
	{			
		throw new PendingException();
	}
	
	@And("the entered email is \"([^\"]*)\"")
	public void entered_email(String email) throws Throwable
	{		
		throw new PendingException();
	}
	
	@And("the entered sampling site name is \"([^\"]*)\"")
	public void entered_sampling_site_name(String sampling_site_name) throws Throwable
	{		
		throw new PendingException();
	}
	
	@Then("he should receive a message with \"([^\"]*)\"")
	public void receive_a_message(String status) throws Throwable
	{		
	    throw new PendingException();
	}
	
	// Scenario Outline: Verification of email
	
	@Given("an email input field on page \"([^\"]*)\"")
	public void existing_email_field(String name) throws Throwable
	{			
		throw new PendingException();
	}
	
	@When("the email input is \"([^\"]*)\"")
	public void verify_email(String email) throws Throwable
	{
		throw new PendingException();
	}
	
	@Then("the email is \"([^\"]*)\"")
	public void check_validation_status_email(String status) throws Throwable
	{
		throw new PendingException();
	}
	
	// Scenario Outline: Verification of contact name
	
	@Given("contact name input fields on page \"([^\"]*)\"")
	public void existing_name_input_fields(String name) throws Throwable
	{			
		throw new PendingException();
	}
	
	@When("the contact first name is \"([^\"]*)\"")
	public void verify_first_name(String email) throws Throwable
	{
		throw new PendingException();
	}
	
	@And("the contact last name is \"([^\"]*)\"")
	public void verify_last_name(String email) throws Throwable
	{
		throw new PendingException();
	}
	
	@Then("the contact name is \"([^\"]*)\"")
	public void check_validation_status_name(String status) throws Throwable
	{
		throw new PendingException();
	}
}
