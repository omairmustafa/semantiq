package readibility;


import in.tum.de.sebis.callers.EntityExtraction;
import in.tum.de.sebis.callers.KeywordExtraction;
import in.tum.de.sebis.callers.URLSummarizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import models.TextSummarizer;
import models.Tokenizer;
import opennlp.NameFinder;
import opennlp.SentenceDetector;

public class TextProcessor {


	private List<String> sentences = null;
	private String[] tokens = null;
	private int syllableCount = 0;
	private String text;
	private String summary;
	private List<String> imageUrls;
	private HashMap<String, Float> keywordRelevance = new HashMap<String, Float>();
	private String names = "";
	
	public HashMap<String, Float> getKeywordRelevance() {
		return keywordRelevance;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<String> getSentences() {
		return sentences;
	}

	public String[] getTokens() {
		return tokens;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public int getSyllableCount() {
		return syllableCount;
	}
	public String getText() {
		return text;
	}

	public String getNames() {
		return names;
	}

	public TextProcessor(String text) {
	
		this.tokens = Tokenizer.tokenize(text);
		this.sentences = parseSentences(text);
		this.syllableCount = calculateSyllables(this.tokens);
		this.text = text;
		this.names = parseNames(this.tokens);
		this.keywordRelevance = getTopTenKeywords(text);
		this.summary = summarizeText(text);
		
//		this.summary = EntityExtraction.getEntities(text);
		
	}
	
	private HashMap<String, Float> getTopTenKeywords (String text) {
		
		return KeywordExtraction.getTopTenKeywords(text);
	}
	
	private String summarizeText(String text) {
		
		TextSummarizer summariser = new TextSummarizer();
		return summariser.summarise(text, 5);
		
	}
	
	private List<String> parseSentences(String text){
		
		List<String> sentences = new ArrayList<String>();

		if(text.length() > 0) {

			try {
				sentences = SentenceDetector.detectSentences(text);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sentences;
	}
	
	private int calculateSyllables (String[] tokens)  {
		int syllableCount = 0;
		
		for (String str : tokens){
			syllableCount += EnglishSyllableCounter.countSyllables(str);
		}
		
		return syllableCount;
	}
	
	private String parseNames (String[] tokens){

		List<String> namesList = NameFinder.findNames(tokens);
		StringBuilder names = new StringBuilder();
		
		for (int i = 0; i < namesList.size(); i++) {
			
			names.append(namesList.get(i));
			
			if(i != namesList.size() - 1)
				names.append(", ");
		}
		
		return names.toString();
		
	}

}
