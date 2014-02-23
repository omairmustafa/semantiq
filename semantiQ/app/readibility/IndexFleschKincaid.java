package readibility;

public class IndexFleschKincaid implements IReadabilityIndex {

	private double rating = 0.0;
	
	public void calculate(int totalWords, int totalSentences, int totalSyllables){
		
		//Implementation of Flesch-Kincaid Reading Ease formula
		rating = (double) (206.835 - 1.015 * (totalWords/(double)totalSentences) - 84.6 * (totalSyllables/(double)totalWords));
		
		
//		System.out.println(totalWords + " " + totalSyllables);
	}
	
	@Override
	public double getRating() {
		return rating;
	}

}
