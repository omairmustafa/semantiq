package readibility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.NameFinder;
import models.Semantics;
import models.SentenceDetector;
import models.Tokenizer;

import opennlp.tools.util.InvalidFormatException;

public class TextProcessor {


	private List<String> sentences = null;
	private String[] tokens = null;
	private int syllableCount = 0;
	private String text;
	private List<String> names = null;
	
	public List<String> getSentences() {
		return sentences;
	}

	public String[] getTokens() {
		return tokens;
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

	
//	public Semantics processText(String text) throws InvalidFormatException, IOException {
//
//	
//		int syllableCount = 0;
//		Semantics semantics = null;
//		String[] tokens;
//		semantics = new Semantics();
//		List<String> sentenceList = null;
//		ReadibilityIndexes indexes = new ReadibilityIndexes();
//
//		sentenceList = detectSentences(text);
//		tokens = Tokenizer.tokenize(text);
//
//		//Calculating indices
//		semantics.setParagraph(text);
//		semantics.setNumOfSentences(sentenceList.size());
//		semantics.setNumOfWords(tokens.length);
//		semantics.setARI(indexes.calculateARI(text, tokens.length, sentenceList.size()));
//		semantics.setFKI(indexes.calculateFleschKincaidIndex(tokens.length, sentenceList.size(), syllableCount));
//		semantics.setCLI(indexes.calculateColemanLiauIndex(text.replaceAll("[^\\w]", "").length(), tokens.length, sentenceList.size()));
//		semantics.setNames(NameFinder.findNames(tokens).toString());
//		semantics.setDCI(indexes.calculateDaleChallIndex(tokens, tokens.length, sentenceList.size()));
//		//			semantics.setSMOG(calculateSMOG(paragraph));
//		
//		return semantics;
//	}

//	static List<String> detectSentences(String text){
//		
//		List<String> sentenceList = new ArrayList();
//
//		if(text.length() > 0) {
//
//			try {
//				sentenceList = SentenceDetector.detectSentences(text);
//			} 
//			catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return sentenceList;
//	}
	
}
