package testSteps;

import java.lang.reflect.Method;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class StepsForFeature2 {

	@Before({ "@scenario1Tag_in_feature2", "@scenario2Tag_in_feature2 ", "@scenarioOutlineTag_in_feature2 " })
	public void beforeMethodHookForScenariosInFeature2() {

	}

	@After({ "@scenario1Tag_in_feature2", "@scenario2Tag_in_feature2 ", "@scenarioOutlineTag_in_feature2 " })
	public void afterMethodHookForScenariosInFeature2() {

	}

	@Given("^given statement for background in feature2$")
	public void given_statement_for_background_in_feature2() {

	}

//scenario1
	@Given("^given statement for scenario1 in feature2$")
	public void given_statement_for_scenario1_in_feature2() {

	}

	@And("^given&and statement for scenario1 in feature2$")
	public void given_and_statement_for_scenario1_in_feature2() {

	}

	@Then("^then statement for scenario1 in feature2$")
	public void then_statement_for_scenario1_in_feature2() {

	}

	@And("^then&and statement for scenario1 in feature2$")
	public void then_and_statement_for_scenario1_in_feature2() {

	}

	@But("^but statement for scenario1 in feature2$")
	public void but_statement_for_scenario1_in_feature2() {

	}

	@And("^but&and statement for scenario1 in feature2$")
	public void but_and_statement_for_scenario1_in_feature2() {
	}

//scenario2
	@Given("^given statement for scenario2 in feature2$")
	public void given_statement_for_scenario2_in_feature2() {

	}

	@And("^given&and statement for scenario2 in feature2$")
	public void given_and_statement_for_scenario2_in_feature2() {

	}

	@Then("^then statement for scenario2 in feature2$")
	public void then_statement_for_scenario2_in_feature2() {

	}

	@And("^then&and statement for scenario2 in feature2$")
	public void then_and_statement_for_scenario2_in_feature2() {

	}

	@But("^but statement for scenario2 in feature2$")
	public void but_statement_for_scenario2_in_feature2() {

	}

	@And("^but&and statement for scenario2 in feature2$")
	public void but_and_statement_for_scenario2_in_feature2() {

	}

	// scenarioOutline
	@Given("^given statement for scenarioOutline in feature2 with \"([^\"]*)\"$")
	public void given_statement_for_scenarioOutline_in_feature2_with_parameter(String s) {

	}

	@And("^given&and statement for scenarioOutline in feature2 with \"([^\"]*)\"$")
	public void given_and_statement_for_scenarioOutline_in_feature2_with_parameter(String s) {

	}

	@Then("^then statement for scenarioOutline in feature2 with \"([^\"]*)\"$")
	public void then_statement_for_scenarioOutline_in_feature2_with_parameter(String s) {

	}

	@Then("^then&and statement for scenarioOutline in feature2 with \"([^\"]*)\"$")
	public void then_and_statement_for_scenarioOutline_in_feature2_with_parameter(String s) {

	}

	@But("^but statement for scenarioOutline in feature2 with \"([^\"]*)\"$")
	public void but_statement_for_scenarioOutline_in_feature2_with_parameter(String s) {

	}

	@And("^but&and statement for scenarioOutline in feature2 with \"([^\"]*)\"$")
	public void but_and_statement_for_scenarioOutline_in_feature2_with_parameter(String s) {

	}

}
