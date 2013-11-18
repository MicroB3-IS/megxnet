package net.megx.pageobjects.osd.registry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class OSD_AdminEditeDeletePage 
{
	public WebElement input_1;
	public WebElement input_2;
	
	
	public OSD_AdminEditeDeletePage (WebDriver driver) 
	{
	    PageFactory.initElements(driver, this);
	}


}
