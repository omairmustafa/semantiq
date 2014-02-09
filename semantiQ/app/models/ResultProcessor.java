package models;

import image.AvatarManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import readibility.ReadablityIndexFactory;
import readibility.TextProcessor;


public class ResultProcessor {
	
	DecimalFormat df = new DecimalFormat("#.00");
  
	public Semantics getSemantics (String text){
		
		Semantics semantics = new Semantics();
		HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map = this.getIndexesResult(text);
		this.populateIndexScores(map, semantics);
		this.populateStats(text, semantics);
		this.populateIndexInterpretations(semantics);
		this.populateOverallDifficultyLevel(semantics);
		
		return semantics;
		
	}
	
	private void populateIndexInterpretations(Semantics semantics) {
		
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
			difficultyLevel = "Medium";		
		else if (indexScore > 30 && indexScore <= 50)
			difficultyLevel = "Difficult";
		else if (indexScore >= 0 && indexScore <= 30)
			difficultyLevel = "Complex";

		return difficultyLevel;
	}
	
	private String calculateDifficultyLevel (double indexScore) {
		
		String difficultyLevel = "";
		
		if (indexScore < 6)
			difficultyLevel = "Novice";
		else if (indexScore > 6 && indexScore <= 9)
			difficultyLevel = "Easy";
		else if (indexScore > 9 && indexScore <= 13)
			difficultyLevel = "Medium";
		else if (indexScore > 13 && indexScore <= 16)
			difficultyLevel = "Difficult";
		else if (indexScore > 16)
			difficultyLevel = "Complex";
			
		return difficultyLevel;
	}
	
	private void populateOverallDifficultyLevel (Semantics semantics) {

		int median = 0;
		String overallDifficultyLevel = "";
		List<Integer> indexScores = new ArrayList<Integer>();
		
		indexScores.add(getDifficultyScoreAgainstLevel(semantics.getAutomatedReadibilityDiffucultyLevel()));
		indexScores.add(getDifficultyScoreAgainstLevel(semantics.getColemanLiauDiffucultyLevel()));
		indexScores.add(getDifficultyScoreAgainstLevel(semantics.getDaleChallDiffucultyLevel()));
		indexScores.add(getDifficultyScoreAgainstLevel(semantics.getFleschReadingEaseDiffucultyLevel()));
		indexScores.add(getDifficultyScoreAgainstLevel(semantics.getSmogDiffucultyLevel()));
		
		Collections.sort(indexScores);
		
		median = indexScores.get(3);
		
		if (median == 1)
			overallDifficultyLevel = "Novice";
		else if (median == 2)
			overallDifficultyLevel = "Easy";
		else if (median == 3)
			overallDifficultyLevel = "Medium";
		else if (median == 4)
			overallDifficultyLevel = "Difficult";
		else if (median == 5)
			overallDifficultyLevel = "Complex";

		semantics.setOverallDifficultyLevel(overallDifficultyLevel);
	}
	
	private int getDifficultyScoreAgainstLevel (String difficultyLevel) {
		
		int difficultyScore = 1;
		
		if (difficultyLevel.equals("Novice"))
			difficultyScore = 1;
		else if (difficultyLevel.equals("Easy"))
			difficultyScore = 2;
		else if (difficultyLevel.equals("Medium"))
			difficultyScore = 3;
		else if (difficultyLevel.equals("Difficult"))
			difficultyScore = 4;
		else if (difficultyLevel.equals("Complex"))
			difficultyScore = 5;
		
		return difficultyScore;
	}
	
	private void populateIndexScores(HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map, Semantics semantics) {
		
		// {ARI, COLEMAN_LIAU, DALE_CHALL, SMOG, FLESCH_KINCAID}
		semantics.setAutomatedReadibilityIndex(Double.valueOf(df.format(map.get(ReadablityIndexFactory.ReadablityIndex.ARI))));
		semantics.setColemanLiauIndex(Double.valueOf(df.format(map.get(ReadablityIndexFactory.ReadablityIndex.COLEMAN_LIAU))));
		semantics.setDaleChallIndex(Double.valueOf(df.format(map.get(ReadablityIndexFactory.ReadablityIndex.DALE_CHALL))));
		semantics.setSmogIndex(Double.valueOf(df.format(map.get(ReadablityIndexFactory.ReadablityIndex.SMOG))));
		semantics.setFleschReadingEaseScore(Double.valueOf(df.format(map.get(ReadablityIndexFactory.ReadablityIndex.FLESCH_KINCAID))));
	}
	
	private void populateStats(String text, Semantics semantics) {
		TextProcessor processor = new TextProcessor(text);
		List<String> names = processor.getNames();
		semantics.setText(processor.getText());
		semantics.setSummary(processor.getSummary());
		semantics.setNumOfWords(processor.getTokens().length);
		semantics.setNumOfSentences(processor.getSentences().size());
		semantics.setNames(names.toString());
		semantics.setKeywordRelevance(processor.getKeywordRelevance());
		
		//Send only the top ten keywords as per relevance sorting
		this.populateImageUrls(processor.getKeywordRelevance(), semantics);
		
	}
	
	private void populateImageUrls(HashMap<String, Float> keywords, Semantics semantics) {

		AvatarManager manager = new AvatarManager();
		List<String> urls =  new  ArrayList<String>();
		Random randomNumberGenerator = new Random();
		
		for (Map.Entry<String, Float> entry : keywords.entrySet())	{
			
			List<String> imagesUrls = manager.getImageUrls(entry.getKey());
			String url;
			
			int imageIndex = randomNumberGenerator.nextInt(imagesUrls.size());
			
			if(imagesUrls.size() > 0) {
				url = imagesUrls.get(imageIndex);
				urls.add(url);
				
				if (urls.size() >=10) break;
				
			}//if
		}

		semantics.setImagesUrl(urls);	
	}
	
	private void populateNameImagesUrl(List<String> names, Semantics semantics) {
		
		AvatarManager manager = new AvatarManager();
		List<String> urls =  new  ArrayList<String>(); 
		for(String name: names) {
			List<String> imagesUrls = manager.getImageUrls(name);
			String url;
			
			int index = 0;//To get first image only
			if(imagesUrls.size() > 0) {
				url = imagesUrls.get(index);
				urls.add(url);
			
			}//if
		}//for
		
		System.out.println(">>>> urls" + urls);
		
		semantics.setImagesUrl(urls);	
	}
	
	private HashMap<ReadablityIndexFactory.ReadablityIndex, Double> getIndexesResult(String text) {

		ReadablityIndexFactory indexFactory = new ReadablityIndexFactory();
		HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map = new HashMap<ReadablityIndexFactory.ReadablityIndex, Double>();
		for (ReadablityIndexFactory.ReadablityIndex index : ReadablityIndexFactory.ReadablityIndex.values()) {
			double result =  indexFactory.getReadablityIndexRating(index, text);
			map.put(index, result);

		}

		return map;
	}
	
}