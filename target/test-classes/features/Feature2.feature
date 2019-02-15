#Author: Author2
@feature2Tag 
Feature: Feature2 
Background: Background for feature2 
	Given given statement for background in feature2 
	
@scenario1Tag_in_feature2 
Scenario: Scenario1 for Feature2 
	Given given statement for scenario1 in feature2 
	And given&and statement for scenario1 in feature2 
	Then then statement for scenario1 in feature2 
	And then&and statement for scenario1 in feature2 
	But but statement for scenario1 in feature2 
	And but&and statement for scenario1 in feature2 
	
@scenario2Tag_in_feature2 
Scenario: Scenario2 for Feature2 
	Given given statement for scenario2 in feature2 
	And given&and statement for scenario2 in feature2 
	Then then statement for scenario2 in feature2 
	And then&and statement for scenario2 in feature2 
	But but statement for scenario2 in feature2 
	And but&and statement for scenario2 in feature2 
	
@scenarioOutlineTag_in_feature2 
Scenario Outline: ScenarioOutline for Feature2 
	Given given statement for scenarioOutline in feature2 with "<parameter1>" 
	And given&and statement for scenarioOutline in feature2 with "<parameter2>" 
	Then then statement for scenarioOutline in feature2 with "<parameter3>" 
	And then&and statement for scenarioOutline in feature2 with "<parameter4>" 
	But but statement for scenarioOutline in feature2 with "<parameter5>" 
	And but&and statement for scenarioOutline in feature2 with "<parameter6>" 
	Examples: 
		|parameter1|parameter2|parameter3|parameter4|parameter5|parameter6|
		|A|B|C|D|E|F|
		|1|2|3|4|5|6|	