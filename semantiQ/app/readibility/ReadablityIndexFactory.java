package readibility;

import java.util.List;


public class ReadablityIndexFactory {

	public static enum ReadablityIndex {ARI, COLEMAN_LIAU, DALE_CHALL, SMOG, FLESCH_KINCAID}
	
	private IReadabilityIndex getReadablityIndex(ReadablityIndex index) {

		IReadabilityIndex  readablityIndex = null;

		switch (index) {

		case ARI:
			readablityIndex = new IndexARI();
			break;
		case COLEMAN_LIAU:
			readablityIndex = new IndexColemanLiau();
			break;
		case DALE_CHALL:
			readablityIndex = new IndexDaleChall();
			break;
		case SMOG:
			readablityIndex = new IndexSMOG();
			break;
		case FLESCH_KINCAID:
			readablityIndex = new IndexFleschKincaid();
			break;

		default:
			break;
		}

		return readablityIndex;

	}

	public double getReadablityIndexRating(ReadablityIndex index, String text) {

		IReadabilityIndex  readablityIndex = this.getReadablityIndex(index);
		TextProcessor processor = new TextProcessor(text);
		List<String> sentences = processor.getSentences();
		String[] tokens = processor.getTokens();
		int syllablesCount = processor.getSyllableCount();
		this.calculateReadabilityIndex(readablityIndex, text, sentences, tokens, syllablesCount);

		return readablityIndex.getRating();

	}
	
	private void calculateReadabilityIndex(IReadabilityIndex readablityIndex, String text, List<String> sentences, String[] tokens, int syllablesCount) {

		int totalWords = tokens.length;
		int totalSentences = sentences.size();
		int totalCharactersInText = text.length();
	
		if(readablityIndex instanceof IndexARI) {
			((IndexARI) readablityIndex).calculate(totalCharactersInText, totalWords, totalSentences);

		}
		else if(readablityIndex instanceof IndexColemanLiau) {
			totalCharactersInText = text.replaceAll("[^\\w]", "").length();
			((IndexColemanLiau) readablityIndex).calculate(totalCharactersInText, totalWords, totalSentences);
		}
		else if(readablityIndex instanceof IndexDaleChall) {

			((IndexDaleChall) readablityIndex).calculate(tokens, totalWords, totalSentences);
		}
		else if(readablityIndex instanceof IndexSMOG) {
			((IndexSMOG) readablityIndex).calculate(sentences, tokens);

		}
		else if(readablityIndex instanceof IndexFleschKincaid) {
			((IndexFleschKincaid) readablityIndex).calculate(totalWords, totalSentences, syllablesCount);

		}

	}
	
}
