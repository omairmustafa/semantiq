package readibility;

import java.util.List;

import models.Tokenizer;

public class IndexSMOG implements IReadabilityIndex {

	private double rating = 0.0;

	public void calculate(List<String> sentences, String[] tokens) {

		int passageThreshold = 30;
	
		if (sentences.size() < passageThreshold){
			this.rating = this.calculateSMOGgrade(getPolySyllablesCount(tokens), sentences.size());
		}
		else{
			//Get first, mid and last 10 sentences, calculate polySyllables and call func
			int interval = 10;
			int lowerBound = 0;
			int upperBound = interval - 1; // by default we will get 1st 10 sentences.
			StringBuilder result = new StringBuilder();
			result.append(smogSentences(lowerBound, upperBound, sentences));			

			lowerBound = (sentences.size()/2 -  (interval/2  - 1));
			upperBound = (sentences.size()/2 +  (interval/2  - 2));
			result.append(smogSentences(lowerBound, upperBound, sentences));

			lowerBound = (sentences.size() - interval);
			upperBound = (sentences.size() - 1);
			result.append(smogSentences(lowerBound, upperBound, sentences));

			tokens = Tokenizer.tokenize(result.toString());
			this.rating = this.calculateSMOGgrade(getPolySyllablesCount(tokens), sentences.size());
		}

	}

	@Override
	public double getRating() {
		// TODO Auto-generated method stub
		return rating;
	}
	
	private String  smogSentences (int lowerBound, int upperBound, List<String> sentenceList){

		StringBuilder result = new StringBuilder();

		for (int bound = lowerBound; bound <= upperBound; bound++) {
			result.append(sentenceList.get(bound));

		}
		return result.toString();
	}

	private int getPolySyllablesCount (String[] tokens){
		int count = 0;
		int polySyllableThreshold = 3;
		for (String str : tokens){
			if (EnglishSyllableCounter.countSyllables(str) >= polySyllableThreshold)
				count++;
		}
//		System.out.println("Polysyllables: " + count);
		return count;
	}
	
	public double calculateSMOGgrade(int totalPolysyllables, int totalSentences) {
		double smogGrade = 0;
		
		smogGrade = 1.0430 * Math.sqrt((double)totalPolysyllables * ((double)30 / totalSentences)) + 3.1291;
		
		return smogGrade;
	}

}
