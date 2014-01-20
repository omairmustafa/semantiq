package models;

import java.text.DecimalFormat;
import java.util.HashMap;


import readibility.ReadablityIndexFactory;
import readibility.TextProcessor;


public class ResultProcessor {
	
	DecimalFormat df = new DecimalFormat("#.00");
  
	public Semantics getSemantics (String text){
		
		Semantics semantics = new Semantics();
		HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map = this.getIndexesResult(text);
		this.populateSemanticsForIndexes(map, semantics);
		this.populateSemantics(text, semantics);
		this.populateSemanticsForIndexInterpretation(semantics);
		
		return semantics;
		
	}
	
	private void populateSemanticsForIndexInterpretation(Semantics semantics) {
		
		semantics.setFleschReadingEaseDiffucultyLevel(calculateFleschReadingEaseDifficultyLevel(semantics.getFleschReadingEaseScore()));
		semantics.setAutomatedReadibilityDiffucultyLevel(calculateDifficultyLevel(semantics.getAutomatedReadibilityIndex()));
		semantics.setColemanLiauDiffucultyLevel(calculateDifficultyLevel(semantics.getColemanLiauIndex()));
		semantics.setDaleChallDiffucultyLevel(calculateDifficultyLevel(semantics.getDaleChallIndex()));
		semantics.setSmogDiffucultyLevel(calculateDifficultyLevel(semantics.getSmogIndex()));
	}
	
	private String calculateFleschReadingEaseDifficultyLevel (double indexScore){
		
		String difficultyLevel = "";

		if (indexScore > 90 && indexScore <= 100)
			difficultyLevel = "Novice";
		else if (indexScore > 70 && indexScore <= 90)
			difficultyLevel = "Easy";
		else if (indexScore > 50 && indexScore <= 70)
			difficultyLevel = "Moderate";		
		else if (indexScore > 30 && indexScore <= 50)
			difficultyLevel = "Difficult";
		else if (indexScore >= 0 && indexScore <= 30)
			difficultyLevel = "Complex";

		return difficultyLevel;
	}
	
	private String calculateDifficultyLevel(double indexScore) {
		
		String difficultyLevel = "";
		
		if (indexScore < 6)
			difficultyLevel = "Novice";
		else if (indexScore > 6 && indexScore <= 9)
			difficultyLevel = "Easy";
		else if (indexScore > 9 && indexScore <= 13)
			difficultyLevel = "Moderate";
		else if (indexScore > 13 && indexScore <= 16)
			difficultyLevel = "Difficult";
		else if (indexScore > 16)
			difficultyLevel = "Complex";
			
		return difficultyLevel;
	}
	
	private void populateSemanticsForIndexes(HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map, Semantics semantics) {
		
		// {ARI, COLEMAN_LIAU, DALE_CHALL, SMOG, FLESCH_KINCAID}
		semantics.setAutomatedReadibilityIndex(map.get(ReadablityIndexFactory.ReadablityIndex.ARI));
		semantics.setColemanLiauIndex(map.get(ReadablityIndexFactory.ReadablityIndex.COLEMAN_LIAU));
		semantics.setDaleChallIndex((map.get(ReadablityIndexFactory.ReadablityIndex.DALE_CHALL)));
		semantics.setSmogIndex((map.get(ReadablityIndexFactory.ReadablityIndex.SMOG)));
		semantics.setFleschReadingEaseScore((map.get(ReadablityIndexFactory.ReadablityIndex.FLESCH_KINCAID)));
	}
	
	private void populateSemantics(String text, Semantics semantics) {
		TextProcessor processor = new TextProcessor(text);
		
		semantics.setText(processor.getText());
		semantics.setSummary(processor.getSummary());
		semantics.setNames(processor.getNames().toString());
		semantics.setNumOfSentences(processor.getSentences().size());
		semantics.setNumOfWords(processor.getTokens().length);
	}
	
	private HashMap<ReadablityIndexFactory.ReadablityIndex, Double> getIndexesResult(String text){

		ReadablityIndexFactory indexFactory = new ReadablityIndexFactory();
		HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map = new HashMap<ReadablityIndexFactory.ReadablityIndex, Double>();
		for (ReadablityIndexFactory.ReadablityIndex index : ReadablityIndexFactory.ReadablityIndex.values()) {
			double result =  indexFactory.getReadablityIndexRating(index, text);
			map.put(index, result);

		}

		return map;
	}
}