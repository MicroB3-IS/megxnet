package net.megx.osd.registry;
import org.openqa.selenium.WebDriver;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegisterNewSamplingSiteStepDefs 
{
	protected WebDriver driver;
	
	@Before
	public void setUp() 
	{
		//driver = new FirefoxDriver();
	}
	@Given("the administrator is on the osd_admin_register page \"([^\"]*)\"") 
	public void the_administrator_is_on_the_osd_admin_register_page(String url) throws Throwable
	{
	    //driver.get("http://www.megx.net/bls/bla");		
		throw new PendingException();
	}

	@When("he submits data by clicking the save button")
	public void he_submits_data_by_clicking_the_save_button() throws Throwable
	{
		//driver.findElement(By.id("<id>")).click();		
		throw new PendingException();
	}
	
	@Then("ensure the data were stored by displaying the message")
	public void ensure_the_data_were_stored() throws Throwable
	{
	    //WebElement message = driver.findElement(By.id("<id>"));		
		//assertEquals(message.getText(),"message");		
		throw new PendingException();
	}

	@After
	public void tearDown() 
	{
	    //driver.close();
	}
}
