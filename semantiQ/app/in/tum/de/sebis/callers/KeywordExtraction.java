package in.tum.de.sebis.callers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tum.in.sebis.library.TextProcessor;

public class KeywordExtraction {

	private static HashMap<String, Float> printKeywordNodes(NodeList nodeList)
	{
		String keyword ="";
		float relevance = 0;
		List<String> keywordsList = new ArrayList<String>();
		HashMap<String, Float> keywordRelevance = new HashMap<String, Float>();
		
		for(int i =0; i<nodeList.getLength();i++)
		{
			if(nodeList.item(i).getNodeName()!="#text")
			{
				if(nodeList.item(i).getNodeName()=="text")
				{
					keywordsList.add("\tkeyword: \t" + nodeList.item(i).getTextContent());
					keyword = nodeList.item(i).getTextContent();
					
				}
				else
				{
					keywordsList.add("\t" + nodeList.item(i).getNodeName() + ": \t" + nodeList.item(i).getTextContent());
					relevance = Float.valueOf(nodeList.item(i).getTextContent());
				}
				keywordRelevance.put(keyword,relevance);
			}
		}
		
		return keywordRelevance;
	}

	private static HashMap<String, Float> checkKeywords(Node node)
	{
		HashMap<String, Float> keywordRelevance = new HashMap<String, Float>();
		
		if (node.getNodeName()=="keyword")
		{
			keywordRelevance.putAll(printKeywordNodes(node.getChildNodes()));
		}
		else
		{
			if (node.hasChildNodes()) {
				NodeList nodeList = node.getChildNodes();
				for(int i =0; i<nodeList.getLength();i++)
				{
					Node tempNode = nodeList.item(i);
					keywordRelevance.putAll(checkKeywords(tempNode));
				}
		 	}
		}
		return keywordRelevance;
	}
	
	public static HashMap<String, Float> getAllKeywords (String text) {
		
		TextProcessor textProcesser = TextProcessorFactory.getInstance();
		Document doc;
		String language = "";
		HashMap<String, Float> keywordRelevance = new HashMap<String, Float>();
		StringBuilder result = new StringBuilder();
		
		try {
			doc = textProcesser.TextGetRankedKeywords(text);
			
			//System.out.println(Utility.getStringFromDocument(doc));
			
			language = doc.getElementsByTagName("language").item(0).getTextContent();
			result.append("Language: " + language);
			
			if (doc.hasChildNodes()) {
				NodeList nodeList = doc.getChildNodes();
				for(int i =0; i<nodeList.getLength();i++)
				{
					Node tempNode = nodeList.item(i);
					keywordRelevance.putAll(checkKeywords(tempNode));
				}
		 	}
		} 
		catch (XPathExpressionException | IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return (HashMap<String, Float>) sortByComparator(keywordRelevance);
	}
	
	public static HashMap<String, Float> getTopTenKeywords (String text) {
		
		HashMap<String, Float> allKeywords = getAllKeywords(text);
		HashMap<String, Float> topTenKeywords = new HashMap<String, Float>();
//		
	    int count = 0;
	    int max = 10;

	    for (Map.Entry<String, Float> entry : allKeywords.entrySet()) {

	    	if (count >= max) break;

	    	topTenKeywords.put(entry.getKey(), entry.getValue());
	    	count++;
	    }
	    
	    topTenKeywords = (HashMap<String, Float>) sortByComparator(topTenKeywords);
	    
	    return topTenKeywords;
		
	}
	
	private static Map sortByComparator(Map unsortMap) {
		 
		List list = new LinkedList(unsortMap.entrySet());
 
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
                                       .compareTo(((Map.Entry) (o1)).getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	public static void main(String[] args) {

		String text = "Obama is a graduate of Columbia University and Harvard Law School, where he served as president of the Harvard Law Review. He was a community organizer in Chicago before earning his law degree. He worked as a civil rights attorney and taught constitutional law at the University of Chicago Law School from 1992 to 2004. He served three terms representing the 13th District in the Illinois Senate from 1997 to 2004, running unsuccessfully for the United States House of Representatives in 2000. In 2004, Obama received national attention during his campaign to represent Illinois in the United States Senate with his victory in the March Democratic Party primary, his keynote address at the Democratic National Convention in July, and his election to the Senate in November. He began his presidential campaign in 2007, and in 2008, after a close primary campaign against Hillary Rodham Clinton, he won sufficient delegates in the Democratic Party primaries to receive the presidential nomination. He then defeated Republican nominee John McCain in the general election, and was inaugurated as president on January 20, 2009. Nine months after his election, Obama was named the 2009 Nobel Peace Prize laureate. During his first two years in office, Obama signed into law economic stimulus legislation in response to the Great Recession in the form of the American Recovery and Reinvestment Act of 2009 and the Tax Relief, Unemployment Insurance Reauthorization, and Job Creation Act of 2010. Other major domestic initiatives in his first term include the Patient Protection and Affordable Care Act, often referred to as Obamacare the Dodd–Frank Wall Street Reform and Consumer Protection Act; and the Don't Ask, Don't Tell Repeal Act of 2010. In foreign policy, Obama ended U.S. military involvement in the Iraq War, increased U.S. troop levels in Afghanistan, signed the New START arms control treaty with Russia, ordered U.S. military involvement in Libya, and ordered the military operation that resulted in the death of Osama bin Laden. In November 2010, the Republicans regained control of the House of Representatives as the Democratic Party lost a total of 63 seats, and after a lengthy debate over federal spending and whether or not to raise the nations debt limit, Obama signed the Budget Control Act of 2011 and the American Taxpayer Relief Act of 2012.";
		
		getTopTenKeywords(text);
		
//		HashMap<String, Float> all = getAllKeywords(text);
//		HashMap<String, Float> ten = new HashMap<String, Float>();
//		Map sortedMap = new LinkedHashMap();
//		int count = 0;
//	    int max = 10;
//	    
//	    for (Map.Entry<String, Float> entry : all.entrySet()) {
//		       
//	    	if (count >= max) break;
//
//	       sortedMap.put(entry.getKey(), entry.getValue());
//	       count++;
//	    }
//	    
//		for (Map.Entry<String, Float> entry : sortedMap.entrySet()) {
//		       
//	    	if (count >= max) break;
//
//	       System.out.println("AMMAR Key: " + entry.getKey() + entry.getValue());
//	       count++;
//	    }
		
//		TextProcessor textProcesser = TextProcessorFactory.getInstance();
//		Document doc;
//
//		try {
//			// Extract topic keywords for a web URL.
//			doc = textProcesser.URLGetRankedKeywords("http://www.rantsports.com/soccer/2014/01/09/bayern-munichs-act-of-generosity-to-fans-vs-arsenal-another-example-of-german-footballs-sanity/");
//			//System.out.println(Utility.getStringFromDocument(doc));
//			
//			String url = doc.getElementsByTagName("url").item(0).getTextContent();
//			String language = doc.getElementsByTagName("language").item(0).getTextContent();
//			System.out.println("URL: " + url);
//			System.out.println("Language: " + language);
//			
//			
//			if (doc.hasChildNodes()) {
//				NodeList nodeList = doc.getChildNodes();
//				for(int i =0; i<nodeList.getLength();i++)
//				{
//					Node tempNode = nodeList.item(i);
//					checkKeywords(tempNode);
//				}		
//		 	}
//			
//
//			// Extract topic keywords for a text string.
//			String text = "Bundesliga clubs are always 51 percent owned by the fans, rather than an " +
//							"owner dominated league that is seen in England�s top flight. But I don�t see that as an excuse " +
//							"as to why English club don�t support fans much. Supporters are the integral part of a football club " +
//							"no matter the share of the ownership; they are the thing that keeps it running.";
//			doc = textProcesser.TextGetRankedKeywords(text);
//			System.out.println(Utility.getStringFromDocument(doc));
//			
//			language = doc.getElementsByTagName("language").item(0).getTextContent();
//			System.out.println("\n\n\nText: " + text);
//			System.out.println("Language: " + language);
//			
//			if (doc.hasChildNodes()) {
//				NodeList nodeList = doc.getChildNodes();
//				for(int i =0; i<nodeList.getLength();i++)
//				{
//					Node tempNode = nodeList.item(i);
//					checkKeywords(tempNode);
//				}
//		 	}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
