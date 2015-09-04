package net.megx.pageobjects.osd.registry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class OSD_AdminRegisterPage 
{
	public WebElement input_1;
	public WebElement input_2;
	public WebElement submit_button;
	
	
	public OSD_AdminRegisterPage (WebDriver driver) 
	{
	    PageFactory.initElements(driver, this);
	}


}
