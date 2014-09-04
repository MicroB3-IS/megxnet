package net.megx.osd.registry;

import org.openqa.selenium.WebDriver;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class EditDataStepDefs 
{
    protected WebDriver driver;
	
	@Before
	public void setUp() 
	{
		//driver = new FirefoxDriver();
	}
	@Given("the admin is on edit participant and sampling-site data page \"([^\"]*)\"") 
	public void the_admin_is_on_edit_page(String url) throws Throwable
	{
	    //driver.get(url);		
		throw new PendingException();
	}
	
	@When("he submits modified data by clicking the save button")
	public void he_submits_modified_data_by_clicking_the_save_button() throws Throwable
	{
		//driver.findElement(By.id("<id>")).click();		
		throw new PendingException();
	}
	
	@Then("ensure the modified data were stored by displaying the message \"([^\"]*)\"")
	public void ensure_the_modified_data_were_stored(String msg) throws Throwable
	{
	    //WebElement message = driver.findElement(By.id("<id>"));		
		//assertEquals(message.getText(), msg);		
		throw new PendingException();
	}

	@After
	public void tearDown() 
	{
	    //driver.close();
	}
}
