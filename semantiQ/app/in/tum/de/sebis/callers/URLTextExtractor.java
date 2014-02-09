package in.tum.de.sebis.callers;


import org.w3c.dom.Document;

import de.tum.in.sebis.library.TextProcessor;


public class URLTextExtractor {

	public static String extractTextFromURL(String url) {

		TextProcessor textProcesser = TextProcessorFactory.getInstance();
		Document doc;
		String text = "";
		
		try {
		    
			// Extract page text from a web URL. (ignoring ads, navigation links, and other content).
	        doc = textProcesser.URLGetText(url);
	        //System.out.println(Utility.getStringFromDocument(doc));
	        text = doc.getElementsByTagName("text").item(0).getTextContent();
		    
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return text;
		
	}

}
