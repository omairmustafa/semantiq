package in.tum.de.sebis.callers;

import org.w3c.dom.Document;

import de.tum.in.sebis.library.TextProcessor;

public class Categorization {

	public static void main(String[] args) {

		TextProcessor textProcesser = TextProcessorFactory.getInstance();
		Document doc;

		try {
			
			// Categorize a web URL by topic.
	        doc = textProcesser.URLGetCategory("http://www.footballrealm.com/");
	        //System.out.println(Utility.getStringFromDocument(doc));
	        
	        String url = doc.getElementsByTagName("url").item(0).getTextContent();
	        String language = doc.getElementsByTagName("language").item(0).getTextContent();
		    String category = doc.getElementsByTagName("category").item(0).getTextContent();
	        String score = doc.getElementsByTagName("score").item(0).getTextContent();
		    System.out.println("URL: " + url);
		    System.out.println("Language: " + language);
		    System.out.println("Category: " + category);	        
		    System.out.println("Score: " + score);
		    
	        
	        // Categorize some text.
		    String text = "Obama is a graduate of Columbia University and Harvard Law School, where he served as president of the Harvard Law Review. He was a community organizer in Chicago before earning his law degree. He worked as a civil rights attorney and taught constitutional law at the University of Chicago Law School from 1992 to 2004. He served three terms representing the 13th District in the Illinois Senate from 1997 to 2004, running unsuccessfully for the United States House of Representatives in 2000.";
	        doc = textProcesser.TextGetCategory(text);
	        //System.out.println(Utility.getStringFromDocument(doc));
	        
	        language = doc.getElementsByTagName("language").item(0).getTextContent();
		    category = doc.getElementsByTagName("category").item(0).getTextContent();
	        score = doc.getElementsByTagName("score").item(0).getTextContent();
	        System.out.println("");
		    System.out.println("Text: " + text);
		    System.out.println("Language: " + language);
		    System.out.println("Category: " + category);	        
		    System.out.println("Score: " + score);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
