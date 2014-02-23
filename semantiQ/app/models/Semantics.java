package models;

import java.util.HashMap;
import java.util.List;

public class Semantics {
	
	//Variable declarations
	private String text;
	private String names;
	private String summary;
	private String fleschReadingEaseDiffucultyLevel;
	private String automatedReadibilityDiffucultyLevel;
	private String colemanLiauDiffucultyLevel;
	private String daleChallDiffucultyLevel;
	private String smogDiffucultyLevel;
	private String overallDifficultyLevel;
	private HashMap<String, Float> keywordRelevance;
	private int syllableCount, numOfWords, numOfSentences;
	private double automatedReadibilityIndex, fleschReadingEaseScore, colemanLiauIndex, daleChallIndex, smogIndex;
	private List<String> imagesUrl;
	private List<Entity> entities;
	
	//Getters Setters
	
	public String getOverallDifficultyLevel() {
		return overallDifficultyLevel;
	}
	public HashMap<String, Float> getKeywordRelevance() {
		return keywordRelevance;
	}
	public void setKeywordRelevance(HashMap<String, Float> keywordRelevance) {
		this.keywordRelevance = keywordRelevance;
	}
	public void setOverallDifficultyLevel(String overallDifficultyLevel) {
		this.overallDifficultyLevel = overallDifficultyLevel;
	}
	
	public String getAutomatedReadibilityDiffucultyLevel() {
		return automatedReadibilityDiffucultyLevel;
	}
	public void setAutomatedReadibilityDiffucultyLevel(
			String automatedReadibilityDiffucultyLevel) {
		this.automatedReadibilityDiffucultyLevel = automatedReadibilityDiffucultyLevel;
	}
	public String getColemanLiauDiffucultyLevel() {
		return colemanLiauDiffucultyLevel;
	}
	public void setColemanLiauDiffucultyLevel(String colemanLiauDiffucultyLevel) {
		this.colemanLiauDiffucultyLevel = colemanLiauDiffucultyLevel;
	}
	public String getDaleChallDiffucultyLevel() {
		return daleChallDiffucultyLevel;
	}
	public void setDaleChallDiffucultyLevel(String daleChallDiffucultyLevel) {
		this.daleChallDiffucultyLevel = daleChallDiffucultyLevel;
	}
	public String getSmogDiffucultyLevel() {
		return smogDiffucultyLevel;
	}
	public void setSmogDiffucultyLevel(String smogDiffucultyLevel) {
		this.smogDiffucultyLevel = smogDiffucultyLevel;
	}
	public String getFleschReadingEaseDiffucultyLevel() {
		return fleschReadingEaseDiffucultyLevel;
	}
	public void setFleschReadingEaseDiffucultyLevel(String fleschReadingEaseDiffucultyLevel) {
		this.fleschReadingEaseDiffucultyLevel = fleschReadingEaseDiffucultyLevel;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public double getAutomatedReadibilityIndex() {
		return automatedReadibilityIndex;
	}
	public void setAutomatedReadibilityIndex(double automatedReadibilityIndex) {
		this.automatedReadibilityIndex = automatedReadibilityIndex;
	}
	public double getFleschReadingEaseScore() {
		return fleschReadingEaseScore;
	}
	public void setFleschReadingEaseScore(double fleschReadingEaseScore) {
		this.fleschReadingEaseScore = fleschReadingEaseScore;
	}
	public double getColemanLiauIndex() {
		return colemanLiauIndex;
	}
	public void setColemanLiauIndex(double colemanLiauIndex) {
		this.colemanLiauIndex = colemanLiauIndex;
	}
	public double getDaleChallIndex() {
		return daleChallIndex;
	}
	public void setDaleChallIndex(double daleChallIndex) {
		this.daleChallIndex = daleChallIndex;
	}
	public double getSmogIndex() {
		return smogIndex;
	}
	public void setSmogIndex(double smogIndex) {
		this.smogIndex = smogIndex;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getSyllableCount() {
		return syllableCount;
	}
	public void setSyllableCount(int syllableCount) {
		this.syllableCount = syllableCount;
	}
	public int getNumOfWords() {
		return numOfWords;
	}
	public void setNumOfWords(int numOfWords) {
		this.numOfWords = numOfWords;
	}
	public int getNumOfSentences() {
		return numOfSentences;
	}
	public void setNumOfSentences(int numOfSentences) {
		this.numOfSentences = numOfSentences;
	}
	public List<String> getImagesUrl() {
		return imagesUrl;
	}
	public void setImagesUrl(List<String> imagesUrl) {
		this.imagesUrl = imagesUrl;
	}
	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
}
