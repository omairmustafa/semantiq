package readibility;

public class IndexColemanLiau implements IReadabilityIndex {

	private double rating = 0.0;
	
	public void calculate(int totalLetters, int totalWords, int totalSentences){
		double avgLettersPer100Words; 
		double avgSentencesPer100Words;
		
		avgLettersPer100Words = (double) totalLetters/totalWords * 100;
		avgSentencesPer100Words = (double) totalSentences/totalWords * 100;
		
		rating = (double) ((0.0588 * avgLettersPer100Words) - (0.296 * avgSentencesPer100Words) - 15.8);
		
	}
	
	@Override
	public double getRating() {
		return rating;
	}

}
