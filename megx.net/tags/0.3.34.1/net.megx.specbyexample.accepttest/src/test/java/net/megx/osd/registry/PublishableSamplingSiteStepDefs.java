package net.megx.osd.registry;

import org.openqa.selenium.WebDriver;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PublishableSamplingSiteStepDefs 
{
    protected WebDriver driver;
	
	@Before
	public void setUp() 
	{
		//driver = new FirefoxDriver();
	}
	
	//Scenario: Availability of minimum data for a new Sampling Site,
    //data correctness will be tested with verify-optional-data.feature
	
	@Given("the admin wants check minimum data available on page \"([^\"]*)\"") 
	public void minimum_data_for_sampling_available(String url) throws Throwable
	{
	    //driver.get("http://www.megx.net/bla/bla);		
		throw new PendingException();
	}	
	
	@When ("a minimum of data is available")
	public void check_minimum_data_available(DataTable data) throws Throwable
	{
		throw new PendingException();
	}
	
	@Then("the admin gets a message with")
	public void the_admin_gets_a_message(String message)
	{
		throw new PendingException();
	}
	
	@After
	public void tearDown() 
	{
	    //driver.close();
	}
}
