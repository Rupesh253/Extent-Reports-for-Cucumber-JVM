package testRunners;

import java.util.LinkedList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import gherkin.ast.Scenario;
import gherkin.ast.Step;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.ExamplesTableRow;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Tag;

@CucumberOptions(features = { "src/test/resources/features/" }, glue = { "testSteps" }, tags = {
		"@feature1Tag" }, plugin = { "pretty", "html:./target/junitRunner_htmlReport.html",
				"json:target/junitRunner_jsonReport.json", "rerun:target/junitRunner_rerun.txt" })
public class RunnerForFeature1 extends AbstractTestNGCucumberTests implements ITestListener {
	private TestNGCucumberRunner testNGCucumberRunner;
	public static ExtentReports extentReports = ExtentManager.getInstance();
	public static ThreadLocal<ExtentTest> featureThread = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> scenarioOutlineThread = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> scenarioThread = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<LinkedList<Step>> stepListThread = new ThreadLocal<LinkedList<Step>>();
	public static ThreadLocal<ExtentTest> stepThread = new ThreadLocal<ExtentTest>();
	private boolean scenarioOutlineFlag;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(description = "Runs Cucumber Feature", dataProvider = "feature")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		System.out.println(cucumberFeature.getCucumberFeature());
	}

	@DataProvider(name = "feature")
	public Object[][] getfeatures() {
		return testNGCucumberRunner.provideFeatures();
	}

	// @Test(description = "Runs Cucumber Scenario", dataProvider = "scenarios")
	// public void scenario() {

	// }

	// @DataProvider(name = "scenarios")
	// public Object[][] getScenarios() {
	// return testNGCucumberRunner.provideScenarios();
	// }

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
	}

	public void onTestStart(ITestResult result) {

	}

	public void onTestSuccess(ITestResult result) {

	}

	public void onTestFailure(ITestResult result) {

	}

	public void onTestSkipped(ITestResult result) {

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public void onStart(ITestContext context, Feature feature, Scenario scenario, Step step) {

	}

	public void onFinish(ITestContext context) {
		extentReports.flush();
	}

	public void feature(Feature feature) {
		featureThread.set(
				extentReports.createTest(com.aventstack.extentreports.gherkin.model.Feature.class, feature.getName()));
		ExtentTest test = featureThread.get();

		for (Tag tag : feature.getTags()) {
			test.assignCategory(tag.getName());
		}
	}

	public void scenarioOutline(ScenarioOutline scenarioOutline) {
		scenarioOutlineFlag = true;
		ExtentTest node = featureThread.get().createNode(
				com.aventstack.extentreports.gherkin.model.ScenarioOutline.class, scenarioOutline.getName());
		scenarioOutlineThread.set(node);
	}

	public void examples(Examples examples) {
		ExtentTest test = scenarioOutlineThread.get();

		String[][] data = null;
		List<ExamplesTableRow> rows = examples.getRows();
		int rowSize = rows.size();
		for (int i = 0; i < rowSize; i++) {
			ExamplesTableRow examplesTableRow = rows.get(i);
			List<String> cells = examplesTableRow.getCells();
			int cellSize = cells.size();
			if (data == null) {
				data = new String[rowSize][cellSize];
			}
			for (int j = 0; j < cellSize; j++) {
				data[i][j] = cells.get(j);
			}
		}
		test.info(MarkupHelper.createTable(data));
	}

	public void startOfScenarioLifeCycle(Scenario scenario) {
		if (scenarioOutlineFlag) {
			scenarioOutlineFlag = false;
		}

		ExtentTest scenarioNode;
		if (scenarioOutlineThread.get() != null && scenario.getKeyword().trim().equalsIgnoreCase("Scenario Outline")) {
			scenarioNode = scenarioOutlineThread.get()
					.createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, scenario.getName());
		} else {
			scenarioNode = featureThread.get().createNode(com.aventstack.extentreports.gherkin.model.Scenario.class,
					scenario.getName());
		}

		for (gherkin.ast.Tag tag : scenario.getTags()) {
			scenarioNode.assignCategory(tag.getName());
		}
		scenarioThread.set(scenarioNode);
	}

	public void step(Step step) {
		if (scenarioOutlineFlag) {
			return;
		}
		stepListThread.get().add(step);
	}

	public void result(Result result) {
		if (scenarioOutlineFlag) {
			return;
		}

		if (Result.PASSED.equals(result.getStatus())) {
			stepThread.get().pass(Result.PASSED);
		} else if (Result.FAILED.equals(result.getStatus())) {
			stepThread.get().fail(result.getError());
		} else if (Result.SKIPPED.equals(result)) {
			stepThread.get().skip(Result.SKIPPED.getStatus());
		} else if (Result.UNDEFINED.equals(result)) {
			stepThread.get().skip(Result.UNDEFINED.getStatus());
		}
	}

	public static class ExtentManager {
		public static ExtentReports extentReports;
		static String extentReportFile = System.getProperty("user.dir") + "\\extent.html";

		public static ExtentReports getInstance() {
			if (extentReports == null) {
				createInstance(extentReportFile);
			}

			return extentReports;
		}

		public static ExtentReports createInstance(String fileName) {

			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setDocumentTitle("TestNG Extent Report");
			htmlReporter.config().setEncoding("UTF-8");
			htmlReporter.config().setReportName("Extent Reports for TestNG Tests");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.STANDARD);

			extentReports = new ExtentReports();
			extentReports.setSystemInfo("Host", System.getProperty("host"));
			extentReports.setSystemInfo("IP Address", System.getProperty("ip"));
			extentReports.setSystemInfo("Username", System.getProperty("user.name"));
			extentReports.setSystemInfo("OS", System.getProperty("os.name"));
			extentReports.setSystemInfo("OS Version", System.getProperty("os.version"));
			extentReports.setSystemInfo("Os Arch", System.getProperty("os.arch"));
			extentReports.setAnalysisStrategy(AnalysisStrategy.CLASS);
			extentReports.attachReporter(htmlReporter);

			return extentReports;
		}
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}
}
