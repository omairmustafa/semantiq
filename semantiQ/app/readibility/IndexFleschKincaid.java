package readibility;

public class IndexFleschKincaid implements IReadabilityIndex {

	private double rating = 0.0;
	
	public void calculate(int totalWords, int totalSentences, int totalSyllables){
		
		rating = (double) (206.835 - 1.015 * (totalWords/totalSentences) - 84.6 * (totalSyllables/totalWords));
	}
	
	@Override
	public double getRating() {
		return rating;
	}

}
