package testSteps;

import java.lang.reflect.Method;
import java.util.LinkedList;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.ast.Step;
import testRunners.RunnerForFeature1;

public class StepsForFeature1 extends RunnerForFeature1 {

	@Before({ "@firstScenarioTag_in_first_feature", "@secondScenarioTag_in_first_feature ",
			"@scenarioOutlineTag_in_first_feature " })
	public void beforeMethodHookForScenariosInFeature1() {

	}

	@After({ "@firstScenarioTag_in_first_feature", "@secondScenarioTag_in_first_feature ",
			"@scenarioOutlineTag_in_first_feature " })
	public void afterMethodHookForScenariosInFeature1() {

	}

	@Given("^given statement for background in first feature$")
	public void given_statement_for_background_in_first_feature() throws Throwable {
		stepThread.get().createNode("Given", "");	
	}

//first scenario
	@Given("^given statement for first scenario in first feature$")
	public void given_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@And("^given&and statement for first scenario in first feature$")
	public void given_and_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@When("^when statement for first scenario in first feature$")
	public void when_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@And("^when&and statement for first scenario in first feature$")
	public void when_and_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@When("^when statement for second scenario in first feature$")
	public void when_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

	@And("^when&and statement for second scenario in first feature$")
	public void when_and_statement_for_second_scenario_in_first_feature() throws Throwable {
	}

	@Then("^then statement for first scenario in first feature$")
	public void then_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@And("^then&and statement for first scenario in first feature$")
	public void then_and_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@But("^but statement for first scenario in first feature$")
	public void but_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

	@And("^but&and statement for first scenario in first feature$")
	public void but_and_statement_for_first_scenario_in_first_feature() throws Throwable {

	}

//second scenario
	@Given("^given statement for second scenario in first feature$")
	public void given_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

	@And("^given&and statement for second scenario in first feature$")
	public void given_and_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

	@Then("^then statement for second scenario in first feature$")
	public void then_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

	@And("^then&and statement for second scenario in first feature$")
	public void then_and_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

	@But("^but statement for second scenario in first feature$")
	public void but_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

	@And("^but&and statement for second scenario in first feature$")
	public void but_and_statement_for_second_scenario_in_first_feature() throws Throwable {

	}

//scenarioOutline	
	@Given("^given statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void given_statement_for_scenarioOutline_in_first_feature_with_parameter(String arg1) throws Throwable {

	}

	@And("^given&and statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void given_and_statement_for_scenarioOutline_in_first_feature_with_parameter(String arg1) throws Throwable {

	}

	@When("^when statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void when_statement_for_scenarioOutline_in_first_feature_with(String arg1) throws Throwable {
	}

	@And("^when&and statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void when_and_statement_for_scenarioOutline_in_first_feature_with(String arg1) throws Throwable {

	}

	@Then("^then statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void then_statement_for_scenarioOutline_in_first_feature_with_parameter(String arg1) throws Throwable {

	}

	@And("^then&and statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void then_and_statement_for_scenarioOutline_in_first_feature_with_parameter(String arg1) throws Throwable {

	}

	@But("^but statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void but_statement_for_scenarioOutline_in_first_feature_with_parameter(String arg1) throws Throwable {

	}

	@And("^but&and statement for scenarioOutline in first feature with \"([^\"]*)\"$")
	public void but_and_statement_for_scenarioOutline_in_first_feature_with_parameter(String arg1) throws Throwable {

	}

}
