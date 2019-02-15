package testRunners;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources/features/Feature2.feature", glue = "testSteps", plugin = { "pretty",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter",
		"html:./target/junitRunner_htmlReport.html", "json:target/junitRunner_jsonReport.json",
		"rerun:target/junitRunner_rerun.txt" })
public class RunnerForFeature2 extends AbstractTestNGCucumberTests {
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(description = "Runs Cucumber Feature", dataProvider = "feature")
	public void feature(CucumberFeatureWrapper cucumberFeature) {

	}

	@DataProvider(name = "feature")
	public Object[][] getfeatures() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
	}
}
