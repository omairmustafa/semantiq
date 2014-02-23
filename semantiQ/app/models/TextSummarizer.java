package models;

import net.sf.classifier4J.summariser.ISummariser;
import net.sf.classifier4J.summariser.SimpleSummariser;


public class TextSummarizer implements ISummariser{

	private String summary = "";
	
	@Override
	public String summarise(String text, int summaryLengthInSentences) {

		ISummariser summariser = new SimpleSummariser();
		summary = summariser.summarise(text, summaryLengthInSentences);
//		System.out.println(summary);
		
		return summary;
	}
}
