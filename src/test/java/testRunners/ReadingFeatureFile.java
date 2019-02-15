
package testRunners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.gherkin.model.IGherkinFormatterModel;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.FeatureResultListener;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.TestNgReporter;
import cucumber.deps.difflib.DiffRow.Tag;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.junit.FeatureRunner;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberTagStatement;
import gherkin.formatter.Argument;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import gherkin.pickles.Pickle;

@CucumberOptions(features = { "src/test/resources/features/" }, glue = { "testSteps" }, plugin = { "pretty",
		"html:./target/junitRunner_htmlReport.html", "json:target/junitRunner_jsonReport.json",
		"rerun:target/junitRunner_rerun.txt" })
public class ReadingFeatureFile implements Reporter, Formatter, ITestListener {

	private Runtime runtime;
	private RuntimeOptions runtimeOptions;
	private ResourceLoader resourceLoader;
	private FeatureResultListener resultListener;
	private ClassLoader classLoader;
	private TestNGCucumberRunner testNGCucumberRunner;
	Formatter formatter;
	TestNgReporter reporter;

	public static ThreadLocal<ExtentTest> featureThread = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> scenarioOutlineThread = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> scenarioThread = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<LinkedList<Step>> stepListThread = new ThreadLocal<LinkedList<Step>>();
	public static ThreadLocal<ExtentTest> stepThread = new ThreadLocal<ExtentTest>();
	private boolean scenarioOutlineFlag;
	public static ExtentReports extentReports = ExtentManager.getInstance();

	public ReadingFeatureFile() {
		classLoader = this.getClass().getClassLoader();
		resourceLoader = new MultiLoader(classLoader);

		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(this.getClass());
		runtimeOptions = runtimeOptionsFactory.create();
		reporter = new TestNgReporter(System.out);
		formatter = runtimeOptions.formatter(classLoader);
		ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
		resultListener = new FeatureResultListener(runtimeOptions.reporter(classLoader), runtimeOptions.isStrict());
		runtime = new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);

	}

	public void getFeaturesInfo() { List<CucumberFeature> featuresList =
  testNGCucumberRunner.getFeatures(); for (CucumberFeature f : featuresList) {
  System.out.print(f.getGherkinFeature().getName() + " with tags as ");
  List<gherkin.formatter.model.Tag> featureTagList =
  f.getGherkinFeature().getTags(); for (gherkin.formatter.model.Tag t :
  featureTagList) { System.out.print(t.getName() + "\t"); }
  System.out.println(" and it's elements as "); List<CucumberTagStatement>
  featureElementsList = f.getFeatureElements(); for (CucumberTagStatement t :
  featureElementsList) {
  
  System.out.println(t.getVisualName().replace("Scenario:",
  "").replace("Scenario Outline:", "").trim()); List<Step> stepsList =
  t.getSteps(); for (Step s : stepsList) { System.out.println(s.getKeyword() +
  s.getName()); } } // f.run(runtimeOptions.formatter(classLoader),
  resultListener, runtime); }
  
  }

	public List<String> getFeatures() {
		List<CucumberFeature> features = testNGCucumberRunner.getFeatures();
		List<String> featuresList = new ArrayList<String>();
		for (CucumberFeature f : features) {
			featuresList.add(f.getGherkinFeature().getName());
		}
		return featuresList;
	}

	public List<String> getScenarios() {
		List<CucumberFeature> featuresList = testNGCucumberRunner.getFeatures();
		List<String> scenariosList = new ArrayList<String>();
		for (CucumberFeature f : featuresList) {
			List<CucumberTagStatement> featureElementsList = f.getFeatureElements();
			for (CucumberTagStatement t : featureElementsList) {
				if (t.getVisualName().contains("Scenario:")) {
					scenariosList.add(t.getVisualName().replace("Scenario:", "").trim());
				}
			}
		}
		return scenariosList;
	}

	public List<String> getScenarioOutline() {
		List<CucumberFeature> featuresList = testNGCucumberRunner.getFeatures();
		List<String> scenarioOutlinesList = new ArrayList<String>();
		for (CucumberFeature f : featuresList) {
			List<CucumberTagStatement> featureElementsList = f.getFeatureElements();
			for (CucumberTagStatement t : featureElementsList) {
				if (t.getVisualName().contains("Scenario Outline:")) {
					scenarioOutlinesList.add(t.getVisualName().replace("Scenario Outline:", "").trim());
				}
			}
		}
		return scenarioOutlinesList;
	}

	public List<String> getStepsWithKeywords() {
		ArrayList<FeatureStorage> arrayListOfFeatureStorageWithScenarios = new ArrayList<FeatureStorage>();
		ArrayList<FeatureStorage> arrayListOfFeatureStorageWithScenarioOutline = new ArrayList<FeatureStorage>();
		List<String> stepsWithKeywords = new ArrayList<String>();
		List<CucumberFeature> featuresList = testNGCucumberRunner.getFeatures();
		for (CucumberFeature f : featuresList) {
			List<CucumberTagStatement> featureElementsList = f.getFeatureElements();
			for (CucumberTagStatement t : featureElementsList) {
				if (t.getVisualName().contains("Scenario:")) {
					List<Step> stepsList = t.getSteps();
					for (Step s : stepsList) {
						stepsWithKeywords.add(f.getGherkinFeature().getName() + ":"
								+ t.getVisualName().replace("Scenario:", "").trim() + ":" + s.getKeyword().trim() + ":"
								+ s.getName().trim());
						arrayListOfFeatureStorageWithScenarios
								.add(new FeatureStorage(f, t, s.getKeyword(), s.getName()));
					}
				} else if (t.getVisualName().contains("Scenario Outline:")) {
					List<Step> stepsList = t.getSteps();
					for (Step s : stepsList) {
						stepsWithKeywords.add(f.getGherkinFeature().getName() + ":"
								+ t.getVisualName().replace("Scenario Outline:", "").trim() + ":"
								+ s.getKeyword().trim() + ":" + s.getName().trim());
						arrayListOfFeatureStorageWithScenarioOutline
								.add(new FeatureStorage(f, t, s.getKeyword(), s.getName()));
					}

				}

			}
		}
		for (FeatureStorage f : arrayListOfFeatureStorageWithScenarios) {
			System.out.println(f.feature.getGherkinFeature() + f.cucumberTagStatement.getVisualName() + f.stepKeyword
					+ f.stepName);
		}
		for (FeatureStorage f : arrayListOfFeatureStorageWithScenarioOutline) {
			System.out.println(f.feature.getGherkinFeature() + f.cucumberTagStatement.getVisualName() + f.stepKeyword
					+ f.stepName);
		}

		return stepsWithKeywords;

	}

	public static void main(String[] args) {
		List<String> featuresList = new ReadingFeatureFile().getFeatures();
		for (String f : featuresList) {
			System.out.println(f);
		}
		List<String> scenariosList = new ReadingFeatureFile().getScenarios();
		for (String s : scenariosList) {
			System.out.println(s);
		}
		List<String> scenarioOutlinesList = new ReadingFeatureFile().getScenarioOutline();
		for (String so : scenarioOutlinesList) {
			System.out.println(so);
		}
		List<String> stepsWithKeywords = new ReadingFeatureFile().getStepsWithKeywords();

		for (String swk : stepsWithKeywords) {
			System.out.println(swk);
			String[] split = swk.split(":");

			ExtentTest featureTest = extentReports.createTest(com.aventstack.extentreports.gherkin.model.Feature.class,
					split[0]);
			featureThread.set(featureTest);

			ExtentTest scenarioTest = featureThread.get()
					.createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, split[1]);
			scenarioThread.set(scenarioTest);

			ExtentTest stepTest = scenarioThread.get().createNode(split[2], split[3]).pass("pass");
			stepThread.set(stepTest);

		} // extentReports.flush();

	}

	class FeatureStorage {
		CucumberFeature feature;
		ScenarioOutline scenarioOutline;
		Scenario scenario;
		String stepKeyword;
		String stepName;
		CucumberTagStatement cucumberTagStatement;

		public FeatureStorage(CucumberFeature feature, ScenarioOutline scenarioOutline, Scenario scenario,
				String stepKeyword, String stepName) {
			this.feature = feature;
			this.scenarioOutline = scenarioOutline;
			this.scenario = scenario;
			this.stepKeyword = stepKeyword;
			this.stepName = stepName;
		}

		public FeatureStorage(CucumberFeature feature, CucumberTagStatement cucumberTagStatement, String stepKeyword,
				String stepName) {
			this.feature = feature;
			this.cucumberTagStatement = cucumberTagStatement;
			this.stepKeyword = stepKeyword;
			this.stepName = stepName;
		}
	}

	@Override
	public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) { // TODO
																												// Auto-generated
																												// method
																												// stub

	}

	@Override
	public void uri(String uri) { // TODO Auto-generated method stub

	}

	@Override
	public void feature(Feature feature) {

	}

	@Override public void scenarioOutline(ScenarioOutline scenarioOutline) { //
  TODO Auto-generated method stub
  
  }

	@Override public void examples(Examples examples) { // TODO Auto-generated
  method stub
  
  }

	@Override public void startOfScenarioLifeCycle(Scenario scenario) { // TODO
  Auto-generated method stub
  
  }

	@Override public void background(Background background) { // TODO
  Auto-generated method stub
  
  }

	@Override public void scenario(Scenario scenario) { // TODO Auto-generated
  method stub
  
  }

	@Override
	public void step(Step step) { // TODO Auto-generated method stub

	}

	@Override public void endOfScenarioLifeCycle(Scenario scenario) { // TODO
  Auto-generated method stub
  
  }

	@Override
	public void done() { // TODO Auto-generated method stub

	}

	@Override
	public void close() { // TODO Auto-generated method stub

	}

	@Override
	public void eof() { // TODO Auto-generated method stub

	}

	@Override public void before(Match match, Result result) { // TODO
  Auto-generated method stub
  
  }

	@Override public void result(Result result) { // TODO Auto-generated method
  stub
  
  }

	@Override public void after(Match match, Result result) { // TODO
  Auto-generated method stub
  
  }

	@Override
	public void match(Match match) { // TODO Auto-generated method stub

	}

	@Override public void embedding(String mimeType, byte[] data) { // TODO
  Auto-generated method stub
  
  }

	@Override
	public void write(String text) { // TODO Auto-generated method stub

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

	@Override public void onTestStart(ITestResult result) { // TODO
  Auto-generated method stub
  
  }

	@Override public void onTestSuccess(ITestResult result) { // TODO
  Auto-generated method stub
  
  }

	@Override public void onTestFailure(ITestResult result) { // TODO
  Auto-generated method stub
  
  }

	@Override public void onTestSkipped(ITestResult result) { // TODO
  Auto-generated method stub
  
  }

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) { // TODO Auto-generated method stub

	}

	@Override public void onStart(ITestContext context) { // TODO Auto-generated
  method stub
  
  }

	@Override public void onFinish(ITestContext context) { // TODO Auto-generated
  method stub
  
  }

}
