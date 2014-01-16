package readibility;

public class IndexARI implements IReadabilityIndex {

	private double rating = 0.0;
	
	public void calculate(int totalCharactersInText, int totalWords, int totalSentences) {
		
		rating = (double) (4.71 * (totalCharactersInText/totalWords) + 0.5 * (totalWords/totalSentences) - 21.43);
	}
	
	@Override
	public double getRating() {
		return rating;
	}
}
