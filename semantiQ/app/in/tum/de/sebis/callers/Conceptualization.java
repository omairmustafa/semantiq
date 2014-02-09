package in.tum.de.sebis.callers;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.tum.in.sebis.library.TextProcessor;


public class Conceptualization {
	
		private static void printConceptNodes(NodeList nodeList)
		{
			for(int i =0; i<nodeList.getLength();i++)
			{
				if(nodeList.item(i).getNodeName()!="#text")
				{
					if(nodeList.item(i).getNodeName()=="text" || nodeList.item(i).getNodeName()=="yago")
						System.out.println("\t" + nodeList.item(i).getNodeName() + ": \t\t" + nodeList.item(i).getTextContent());
					else
						System.out.println("\t" + nodeList.item(i).getNodeName() + ": \t" + nodeList.item(i).getTextContent());
				}
			}
		}
		
		private static void checkConcept(Node node)
		{
			if (node.getNodeName()=="concept")
			{
				System.out.println("");
				printConceptNodes(node.getChildNodes());
			}
			else
			{
				if (node.hasChildNodes()) {
					NodeList nodeList = node.getChildNodes();
					for(int i =0; i<nodeList.getLength();i++)
					{
						Node tempNode = nodeList.item(i);
						checkConcept(tempNode);
					}
				
			 	}
			}
		}
		
		public static void main(String[] args) {

		TextProcessor textProcesser = TextProcessorFactory.getInstance();
		Document doc;

		try {
			// Extract concept tags for a web URL.
			doc = textProcesser.URLGetRankedConcepts("www.footballrealm.com");
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
					checkConcept(tempNode);
				}		
		 	}
			

			// Extract concept tags for a text string.
			String text = "The weather is not very cold in Munich these days.";
			doc = textProcesser.TextGetRankedConcepts(text);
			//System.out.println(Utility.getStringFromDocument(doc));
			
			language = doc.getElementsByTagName("language").item(0).getTextContent();
			System.out.println("\n\n\nText: " + text);
			System.out.println("Language: " + language);
			
			if (doc.hasChildNodes()) {
				NodeList nodeList = doc.getChildNodes();
				for(int i =0; i<nodeList.getLength();i++)
				{
					Node tempNode = nodeList.item(i);
					checkConcept(tempNode);
				}
		 	}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
