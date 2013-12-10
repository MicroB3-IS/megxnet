package support;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class GUITest {
	
	private String base_url;
	
	public GUITest(){
			
	}
	public void saveButtonAvailabilityTest() {
        // FirefoxDriver driver = new FirefoxDriver(); does not work!
        // Caused by: org.openqa.selenium.firefox.NotConnectedException: 
        // Failed to start up socket within 45000
		
		base_url = System.getProperty("base.url");		
        HtmlUnitDriver driver = new HtmlUnitDriver(); 

        try {
            driver.get(base_url);
            WebElement saveButton = driver.findElement(By.id("saveButton"));
            
           
        } finally {
            driver.close();
        }
    }   

}
