package net.megx.wod05;

import org.junit.runner.RunWith;

import cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@Cucumber.Options(format = {"pretty", "html:target/cucumber-htmlreport",
"json-pretty:target/cucumber-report.json"})
public class RunCukesTest{
}