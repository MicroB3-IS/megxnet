package net.megx.selenium.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SubmitSamplesTest {
	
	private MyOsdFormPage myOsdPage;
	private Properties p;
	private InputStream in;
	private boolean isChormeBrowser;
	
	@Before
	public void setUp() throws Exception{	   
		this.p = new Properties();
		this.in = new FileInputStream("src/test/resources/selenium.properties");
		p.load(in);		
		isChormeBrowser = Boolean.parseBoolean(p.getProperty("isChromeBrowser"));
		
		if(isChormeBrowser){
			System.setProperty("webdriver.chrome.driver", p.getProperty("chromeDriver"));
			myOsdPage = new MyOsdFormPage(new ChromeDriver());	
		}else{
			System.setProperty("webdriver.gecko.driver", p.getProperty("geckoDriver"));
			myOsdPage = new MyOsdFormPage(new FirefoxDriver());	
		}		
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testSubmitSamples() throws IOException{
				myOsdPage.open()
						 .SubmitSamples()
						 .assertElementExist();
						
	}
}
