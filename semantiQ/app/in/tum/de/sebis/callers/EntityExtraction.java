package in.tum.de.sebis.callers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import models.Entity;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.tum.in.sebis.library.TextProcessor;

public class EntityExtraction {

	private Entity getEntityNode(NodeList nodeList) {
		Entity entity = new Entity();

		for (int i = 0; i < nodeList.getLength(); i++) {

			if (nodeList.item(i).getNodeName() != "#text") {

				String value = nodeList.item(i).getTextContent();

				switch (nodeList.item(i).getNodeName()) {
				case "type":
					entity.setEntityType(value);
					break;
				case "text":
					entity.setEntityName(value);
					break;
				case "disambiguated":
					if (nodeList.item(i).getNodeName() == "disambiguated") {
						Node parentNode = nodeList.item(i);
						for (int j = 0; j < parentNode.getChildNodes()
								.getLength(); j++) {

							Node node = parentNode.getChildNodes().item(j);
							if (node.getNodeName() == "website") {
								entity.setEntityWebsite(node.getTextContent());
							}
						}
					}
					break;
				case "count":
					entity.setEntityOccuranceCount(value);
					break;
				case "relevance":
					double percentage =  Double.parseDouble(value) * 100;
					value = String.format("%.2f", percentage) + "%";
					
					entity.setEntityRelevance(value);
					break;

				default:
					break;
				}

			}
		}

		return entity;
	}

	private List<Entity> checkEntities(Node node) {
		StringBuilder builder = new StringBuilder();
		String entities = "";
		List<Entity> resultList = new ArrayList<Entity>();

		if (node.getNodeName() == "entity") {
			builder.append("");
			resultList.add(getEntityNode(node.getChildNodes()));
		} else {
			if (node.hasChildNodes()) {
				NodeList nodeList = node.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node tempNode = nodeList.item(i);
					resultList.addAll(checkEntities(tempNode));
				}

			}
		}

		builder.append(entities);

		return resultList;
	}

	public List<Entity> getEntities(String text) {
		TextProcessor textProcesser = TextProcessorFactory.getInstance();
		Document doc;
		String entities = "";
		String language = "";
		StringBuilder result = new StringBuilder();
		List<Entity> resultList = new ArrayList<Entity>();

		try {
			if (text.contains("www"))
				doc = textProcesser.URLGetRankedNamedEntities(text);
			else
				doc = textProcesser.TextGetRankedNamedEntities(text);

			language = doc.getElementsByTagName("language").item(0)
					.getTextContent();
			// result.append("\n\n\nText: " + text);
			// result.append("Language: " + language);

			if (doc.hasChildNodes()) {
				NodeList nodeList = doc.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node tempNode = nodeList.item(i);
					resultList = checkEntities(tempNode);
				}
			}

		} catch (XPathExpressionException | IOException | SAXException
				| ParserConfigurationException e) {
			e.printStackTrace();
		}

		return resultList;

	}

}
