#Author: Author1
@feature1Tag 
Feature: First Feature 
Background: Background for first feature 
	Given given statement for background in first feature 
	
@firstScenarioTag_in_first_feature @scenarioID("F1S1") @userStory("F1S1U1") 
Scenario: FirstScenario for First Feature 
	Given given statement for first scenario in first feature 
	And given&and statement for first scenario in first feature 
	When when statement for first scenario in first feature 
	And when&and statement for first scenario in first feature 
	Then then statement for first scenario in first feature 
	And then&and statement for first scenario in first feature 
	But but statement for first scenario in first feature 
	And but&and statement for first scenario in first feature 
	
@secondScenarioTag_in_first_feature @scenarioID("F1S2") @userStory("F1S1U2") 
Scenario: SecondScenario for First Feature 
	Given given statement for second scenario in first feature 
	And given&and statement for second scenario in first feature 
	When when statement for second scenario in first feature 
	And when&and statement for second scenario in first feature 
	Then then statement for second scenario in first feature 
	And then&and statement for second scenario in first feature 
	But but statement for second scenario in first feature 
	And but&and statement for second scenario in first feature 
	
@scenarioOutlineTag_in_first_feature 
Scenario Outline: ScenarioOutline for First Feature 
	Given given statement for scenarioOutline in first feature with "<parameter1>" 
	And given&and statement for scenarioOutline in first feature with "<parameter2>" 
	When when statement for scenarioOutline in first feature with "<parameter3>" 
	And when&and statement for scenarioOutline in first feature with "<parameter4>" 
	Then then statement for scenarioOutline in first feature with "<parameter5>" 
	And then&and statement for scenarioOutline in first feature with "<parameter6>" 
	But but statement for scenarioOutline in first feature with "<parameter7>" 
	And but&and statement for scenarioOutline in first feature with "<parameter8>" 
	Examples: 
		|parameter1|parameter2|parameter3|parameter4|parameter5|parameter6|parameter7|parameter8|
		|A|B|C|D|E|F|G|H|
		|1|2|3|4|5|6|7|8|
		
		
		
	