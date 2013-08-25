package net.megx.osd.registry;
import net.megx.pageobjects.osd.registry.OSD_AdminRegisterPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RegisterNewSamplingSiteStepDefs 
{
    protected WebDriver driver;
    private OSD_AdminRegisterPage admin_register_page;
    
	@Before
	public void setUp() 
	{
		//driver = new FirefoxDriver();	
		//admin_register_page = new OSD_AdminRegisterPage(driver);
	}
	
	@Given("the admin is on input participant data page \"([^\"]*)\"") 
	public void the_admin_is_on_input_page(String url) throws Throwable
	{	    
	    //driver.get("http://www.mpi-bremen.de");
		throw new PendingException();
	}

	@When("he submits data by clicking the save button")
	public void submit_data_by_clicking_the_save_button() throws Throwable
	{
		//admin_register_page.submit_button.click();			
		throw new PendingException();
	}
	
	@Then("ensure the data were stored by displaying the message \"([^\"]*)\"")
	public void ensure_the_data_were_stored(String message) throws Throwable
	{
	    //WebElement message = driver.findElement(By.id("<id>"));		
		//assertEquals(message.getText(),"message");		
	    throw new PendingException();
	}
   
	@After
	public void tearDown() 
	{
	    //driver.close();	
		//driver.quit();
	}
}
