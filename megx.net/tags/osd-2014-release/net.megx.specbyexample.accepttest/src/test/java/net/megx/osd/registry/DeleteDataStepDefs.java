package net.megx.osd.registry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DeleteDataStepDefs 
{
	    protected WebDriver driver;
		
		@Before
		public void setUp() 
		{
			//driver = new FirefoxDriver();
		}
		
		@Given("the admin is on delete participant and sampling site data on page \"([^\"]*)\"") 
		public void the_admin_is_on_delete_page(String url) throws Throwable
		{
		    //driver.get(url);		
			throw new PendingException();
		}
		
		@When("he select one datarecord")
		public void select_one_datarecord() throws Throwable
		{
			//driver.findElement(By.id("<id>")).click();		
			throw new PendingException();
		}
		
		@And("he clicks the ok button of an appearing confirmation panel")
		public void click_confirmation_ok() throws Throwable
		{
			//driver.findElement(By.id("<id>")).click();		
			throw new PendingException();
		}
		
		@Then("the datarecord was removed")
		public void datarecord_was_removed() throws Throwable
		{		    	
			throw new PendingException();
		}

		@After
		public void tearDown() 
		{
		   //driver.close();
		}
}
