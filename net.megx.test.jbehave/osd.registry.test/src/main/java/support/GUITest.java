package support;

import junit.framework.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class GUITest {

	HtmlUnitDriver driver = new HtmlUnitDriver();

	public GUITest() {

	}

	public void pageAvailability(String pagePath) {

		try {
			String baseUrl = System.getProperty("base.url");
			driver.get(baseUrl + pagePath);

		} finally {
			driver.close();
		}
	}

	public void formDataAvailability() {
		// TODO		
	}

	public void buttonAvailability(String pagePath) {

		try {
			String baseUrl = System.getProperty("base.url");
			driver.get(baseUrl + pagePath);
			WebElement saveButton = driver.findElementById("saveButton");						
			
		} finally {
			driver.close();
		}

	}

	public void reponseMessageElement(String pagePath) {
		
		try {
			String baseUrl = System.getProperty("base.url");
			driver.get(baseUrl + pagePath);
			WebElement element = driver.findElementById("responseMessage");						
			
		} finally {
			driver.close();
		}

	}

}
