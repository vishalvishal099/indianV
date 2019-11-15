package com.oracle.babylon;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/oracle/babylon/",
        tags = {"~@ignore"},
        glue= {"com.oracle.babylon"},
        plugin = { "pretty", "html:target/cucumber-reports/cucumber-pretty", "json:target/cucumber-reports/cucumber.json" },
        monochrome = true
)

public class RunCucumberTest {

}

