package models;

public class Semantics {
	private String text;
	private String names = "";
	private int syllableCount, numOfWords, numOfSentences;
	private double automatedReadibilityIndex, fleschKincaidIndex, colemanLiauIndex, daleChallIndex, sMOGIndex;
	
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
	public double getFleschKincaidIndex() {
		return fleschKincaidIndex;
	}
	public void setFleschKincaidIndex(double fleschKincaidIndex) {
		this.fleschKincaidIndex = fleschKincaidIndex;
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
	public void setDaleCchallIndex(double daleChallIndex) {
		this.daleChallIndex = daleChallIndex;
	}
	public double getsMOGIndex() {
		return sMOGIndex;
	}
	public void setsMOGIndex(double sMOGIndex) {
		this.sMOGIndex = sMOGIndex;
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
	
}
