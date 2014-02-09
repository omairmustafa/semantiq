package in.tum.de.sebis.callers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tum.in.sebis.library.TextProcessor;

public class EntityExtraction {
	
		private static String printEntityNodes(NodeList nodeList)
		{
			StringBuilder builder = new StringBuilder();
			
			for(int i =0; i<nodeList.getLength();i++)
			{
				if(nodeList.item(i).getNodeName()!="#text")
				{
					if(nodeList.item(i).getNodeName()=="type" || nodeList.item(i).getNodeName()=="count" || nodeList.item(i).getNodeName()=="text" || nodeList.item(i).getNodeName()=="disambiguated")
						builder.append("\t" + nodeList.item(i).getNodeName() + ": \t\t" + nodeList.item(i).getTextContent());
					else
						builder.append("\t" + nodeList.item(i).getNodeName() + ": \t" + nodeList.item(i).getTextContent());
				}
			}
			
			return builder.toString();
		}
		
		private static String checkEntities(Node node)
		{
			StringBuilder builder = new StringBuilder();
			String entities = "";
			
			if (node.getNodeName()=="entity")
			{
				builder.append("");
				entities = printEntityNodes(node.getChildNodes());
			}
			else
			{
				if (node.hasChildNodes()) {
					NodeList nodeList = node.getChildNodes();
					for(int i =0; i<nodeList.getLength();i++)
					{
						Node tempNode = nodeList.item(i);
						builder.append(checkEntities(tempNode));
					}
				
			 	}
			}
			
			builder.append(entities);
			
			return builder.toString();
		}

		public static String getEntities(String text) {
			TextProcessor textProcesser = TextProcessorFactory.getInstance();
			Document doc;
			String entities = "";
			String language = "";
			StringBuilder result = new StringBuilder();
			
			try {
				doc = textProcesser.TextGetRankedNamedEntities(text);
				
				language = doc.getElementsByTagName("language").item(0).getTextContent();
//				result.append("\n\n\nText: " + text);
//				result.append("Language: " + language);
				
				if (doc.hasChildNodes()) {
					NodeList nodeList = doc.getChildNodes();
					for(int i =0; i<nodeList.getLength();i++)
					{
						Node tempNode = nodeList.item(i);
						entities = checkEntities(tempNode);
					}
			 	}
				
				result.append(entities);
			} 
			catch (XPathExpressionException | IOException | SAXException
					| ParserConfigurationException e) {
				e.printStackTrace();
			}
			
			
			return result.toString();
			
		}
		
		public static void main(String[] args) {

		TextProcessor textProcesser = TextProcessorFactory.getInstance();
		Document doc;

		try {
			 // Extract a ranked list of named entities for a web URL.
	        doc = textProcesser.URLGetRankedNamedEntities("http://www.techcrunch.com/");
	        //System.out.println(Utility.getStringFromDocument(doc));
	        
	        String url = doc.getElementsByTagName("url").item(0).getTextContent();
			String language = doc.getElementsByTagName("language").item(0).getTextContent();
			System.out.println("URL: " + url);
			System.out.println("Language: " + language);
			
			
			if (doc.hasChildNodes()) {
				NodeList nodeList = doc.getChildNodes();
				for(int i =0; i<nodeList.getLength();i++)
				{
					Node tempNode = nodeList.item(i);
					checkEntities(tempNode);
				}		
		 	}

	        // Extract a ranked list of named entities from a text string.
			String text = "Bundesliga clubs are always 51 percent owned by the fans, rather than an " +
					"owner dominated league that is seen in England�s top flight. But I don�t see that as an excuse " +
					"as to why English club don�t support fans much. Supporters are the integral part of a football club " +
					"no matter the share of the ownership; they are the thing that keeps it running.";
	        doc = textProcesser.TextGetRankedNamedEntities(text);
	        //System.out.println(Utility.getStringFromDocument(doc));
	        
	        language = doc.getElementsByTagName("language").item(0).getTextContent();
			System.out.println("\n\n\nText: " + text);
			System.out.println("Language: " + language);
			
			if (doc.hasChildNodes()) {
				NodeList nodeList = doc.getChildNodes();
				for(int i =0; i<nodeList.getLength();i++)
				{
					Node tempNode = nodeList.item(i);
					checkEntities(tempNode);
				}
		 	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
