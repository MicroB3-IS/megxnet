package main.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import support.GUITest;
import support.WebserviceTest;


public class RegisterNewSamplingSiteSteps {	
	
	WebserviceTest webserviceTest = new WebserviceTest();
	GUITest guiTest = new GUITest();
	
	
	@Given("the admin is on participant data input page")	
	public void givenTheAdminIsOnParticipantDataInputPage() {
		
		webserviceTest.pageAvailablilityRequest("/input-page");
		guiTest.pageAvailability("/input-page");
	}
	
	@When("he submits data")	
	public void whenHeSubmitsData() {
		
		webserviceTest.sendFormDataRequest("participant/new/anne");	
		// TODO implement form fields 
		guiTest.buttonAvailability("/input-page");
	}
	
	@Then("ensure the data were stored by displaying the message $message")		
	public void thenEnsureTheDataWereStoredByDisplayingAMessage(String expectedMessage) {
		
		webserviceTest.ResponseMessageTest(expectedMessage);
		guiTest.reponseMessageElement("/input-page");
	}
}
