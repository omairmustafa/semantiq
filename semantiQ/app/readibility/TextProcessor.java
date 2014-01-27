package readibility;


import java.util.ArrayList;
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
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	private List<String> names = null;
	
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

	public List<String> getNames() {
		return names;
	}

	public TextProcessor(String text) {
	
		this.tokens = Tokenizer.tokenize(text);
		this.sentences = parseSentences(text);
		this.syllableCount = calculateSyllables(this.tokens);
		this.text = text;
		this.names = parseNames(this.tokens);
		this.summary = summarizeText(text);
		//this.imageUrls = GoogleImagesLoader.getGoogleImageUrls(parseNames(this.tokens));
		
	}
	
	private String summarizeText(String text) {
		
		TextSummarizer summariser = new TextSummarizer();
		return summariser.summarise(text, 5);
		
	}
	
	private List<String> parseSentences(String text){
		
		List<String> sentences = new ArrayList();

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
	
	private List<String> parseNames (String[] tokens){

		return NameFinder.findNames(tokens);
		
	}

}
