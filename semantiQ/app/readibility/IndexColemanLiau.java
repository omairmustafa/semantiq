package readibility;

public class IndexColemanLiau implements IReadabilityIndex {

	private double rating = 0.0;
	
	public void calculate(int totalLetters, int totalWords, int totalSentences){
		double avgLettersPer100Words; 
		double avgSentencesPer100Words;
		
		avgLettersPer100Words = totalLetters/(double)totalWords * 100;
		avgSentencesPer100Words = totalSentences/(double)totalWords * 100;
		
		rating = (double) ((0.0588 * avgLettersPer100Words) - (0.296 * avgSentencesPer100Words) - 15.8);
		
	}
	
	@Override
	public double getRating() {
		return rating;
	}

}
