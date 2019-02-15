/*package testRunners;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentXReporter;
import com.aventstack.extentreports.reporter.KlovReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.mongodb.MongoClientURI;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

*//**
 * A cucumber based reporting listener which generates the Extent Report
 *//*
public class From implements Reporter, Formatter {
	private static ExtentReports extentReports = ExtentManager.getInstance();
	private static ExtentHtmlReporter htmlReporter;
	
	private static ThreadLocal<ExtentTest> featureTestThreadLocal = new InheritableThreadLocal<>();
	private static ThreadLocal<ExtentTest> scenarioOutlineThreadLocal = new InheritableThreadLocal<>();
	static ThreadLocal<ExtentTest> scenarioThreadLocal = new InheritableThreadLocal<>();
	private static ThreadLocal<LinkedList<Step>> stepListThreadLocal = new InheritableThreadLocal<>();
	static ThreadLocal<ExtentTest> stepTestThreadLocal = new InheritableThreadLocal<>();
	private boolean scenarioOutlineFlag;

	public From() {

		//stepListThreadLocal.set(new LinkedList<>());
		scenarioOutlineFlag = false;
	}

	public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

	}

	public void uri(String uri) {

	}

	public void feature(Feature feature) {
		featureTestThreadLocal.set(
				extentReports.createTest(com.aventstack.extentreports.gherkin.model.Feature.class, feature.getName()));
		ExtentTest test = featureTestThreadLocal.get();

		for (Tag tag : feature.getTags()) {
			test.assignCategory(tag.getName());
		}
	}

	public void scenarioOutline(ScenarioOutline scenarioOutline) {
		scenarioOutlineFlag = true;
		ExtentTest node = featureTestThreadLocal.get().createNode(
				com.aventstack.extentreports.gherkin.model.ScenarioOutline.class, scenarioOutline.getName());
		scenarioOutlineThreadLocal.set(node);
	}

	public void examples(Examples examples) {
		ExtentTest test = scenarioOutlineThreadLocal.get();

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
		if (scenarioOutlineThreadLocal.get() != null
				&& scenario.getKeyword().trim().equalsIgnoreCase("Scenario Outline")) {
			scenarioNode = scenarioOutlineThreadLocal.get()
					.createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, scenario.getName());
		} else {
			scenarioNode = featureTestThreadLocal.get()
					.createNode(com.aventstack.extentreports.gherkin.model.Scenario.class, scenario.getName());
		}

		for (Tag tag : scenario.getTags()) {
			scenarioNode.assignCategory(tag.getName());
		}
		scenarioThreadLocal.set(scenarioNode);
	}

	public void background(Background background) {

	}

	public void scenario(Scenario scenario) {

	}

	public void step(Step step) {
		if (scenarioOutlineFlag) {
			return;
		}
		stepListThreadLocal.get().add(step);
	}

	public void endOfScenarioLifeCycle(Scenario scenario) {

	}

	public void done() {
		extentReports.flush();
	}

	public void close() {

	}

	public void eof() {

	}

	public void before(Match match, Result result) {

	}

	public void result(Result result) {
		if (scenarioOutlineFlag) {
			return;
		}

		if (Result.PASSED.equals(result.getStatus())) {
			stepTestThreadLocal.get().pass(Result.PASSED);
		} else if (Result.FAILED.equals(result.getStatus())) {
			stepTestThreadLocal.get().fail(result.getError());
		} else if (Result.SKIPPED.equals(result)) {
			stepTestThreadLocal.get().skip(Result.SKIPPED.getStatus());
		} else if (Result.UNDEFINED.equals(result)) {
			stepTestThreadLocal.get().skip(Result.UNDEFINED.getStatus());
		}
	}

	public void after(Match match, Result result) {

	}

	public void match(Match match) {
		Step step = stepListThreadLocal.get().poll();
		String data[][] = null;
		if (step.getRows() != null) {
			List<DataTableRow> rows = step.getRows();
			int rowSize = rows.size();
			for (int i = 0; i < rowSize; i++) {
				DataTableRow dataTableRow = rows.get(i);
				List<String> cells = dataTableRow.getCells();
				int cellSize = cells.size();
				if (data == null) {
					data = new String[rowSize][cellSize];
				}
				for (int j = 0; j < cellSize; j++) {
					data[i][j] = cells.get(j);
				}
			}
		}

		ExtentTest scenarioTest = scenarioThreadLocal.get();
		ExtentTest stepTest = null;

		try {
			stepTest = scenarioTest.createNode(new GherkinKeyword(step.getKeyword()),
					step.getKeyword() + step.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (data != null) {
			Markup table = MarkupHelper.createTable(data);
			stepTest.info(table);
		}

		stepTestThreadLocal.set(stepTest);
	}

	public void embedding(String mimeType, byte[] data) {

	}

	public void write(String text) {

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

}*/