package in.tum.de.sebis.callers;

import de.tum.in.sebis.library.TextProcessor;

public class TextProcessorFactory {
	public static TextProcessor getInstance() {
		
		TextProcessor textProcessor = null;
		
		try {
			textProcessor = TextProcessor
					.GetInstanceFromFile("./public/resources/token.txt");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return textProcessor;
	}
}
