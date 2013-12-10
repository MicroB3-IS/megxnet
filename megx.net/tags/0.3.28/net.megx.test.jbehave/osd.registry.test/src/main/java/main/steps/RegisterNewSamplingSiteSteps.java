package main.steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import support.GUITest;

public class RegisterNewSamplingSiteSteps {
	
	GUITest test = new GUITest();
 
	@Given("the admin is on participant data input page")	
	public void givenTheAdminIsOnParticipantDataInputPage() {
	    // do something		
	}
	@When("he submits data")
	
	public void whenHeSubmitsData() {
	    // do something
	}
	@Then("ensure the data were stored by displaying the message $message")		
	public void thenEnsureTheDataWereStoredByDisplayingAMessage(String message) {
		test.saveButtonAvailabilityTest(); 		
	}
}
