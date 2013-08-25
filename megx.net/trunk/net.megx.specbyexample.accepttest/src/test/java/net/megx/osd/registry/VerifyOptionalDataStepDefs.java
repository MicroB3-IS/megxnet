package net.megx.osd.registry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class VerifyOptionalDataStepDefs 
{
    protected WebDriver driver;
	
	@Before
	public void setUp() 
	{
		//driver = new FirefoxDriver();
	}
	
	// Scenario: Optional Fields existing	
	
	@Given("the admin want input optional data on page \"([^\"]*)\"") 
	public void input_optional_data(String url) throws Throwable
	{
	    //driver.get(url);		
		throw new PendingException();
	}		
	
	@When("all optional data fields are visible")
	public void optional_fields_visible_check(DataTable optional_fields) throws Throwable
	{			
		//throw new PendingException();		
		//driver.findElement(By.id("institute_lat"));
		//driver.findElement(By.id("institute_long"));
		//driver.findElement(By.id("sampling_site_lat"));
		//driver.findElement(By.id("sampling_site_long"));
	}
	
	@Then("he can enter and submit optional data")
	public void enter_and_submit_optional_data() throws Throwable
	{		
		throw new PendingException();
		//driver.findElement(By.id("submit_button")).click();	
	}
	
	// Scenario: Verification of decimal values latitude and longitude
	
	@Given("lat long fields on page \"([^\"]*)\"")
	public void lat_long_fields_on_asd_admin_register_page(String url) throws Throwable
	{
		//driver.get("http://www.megx.net/bla/bla);		
		throw new PendingException();
	}
	
	@When("the lat long input for an according field is \"([^\"]*)\"")
	public void verify_decimal_values(String decimal_value) throws Throwable
	{
		throw new PendingException();
		//driver.findElement(By.id("institute_lat")).sendKeys(decimal_value);
		//driver.findElement(By.id("institute_long")).sendKeys(decimal_value);;
		//driver.findElement(By.id("sampling_site_lat")).sendKeys(decimal_value);;
		//driver.findElement(By.id("sampling_site_long")).sendKeys(decimal_value);;
	}
	
	@Then("the decimal values are \"([^\"]*)\"")
	public void the_decimal_values_are(String status) throws Throwable
	{
		throw new PendingException();
	}
	
	@After
	public void tearDown() 
	{
	    //driver.close();
	}
}
