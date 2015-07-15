package net.megx.wod05;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "html:target/cucumber-html-report",
		"json:target/cucumber-json-report.json" })
public class RunCukesIT {
}