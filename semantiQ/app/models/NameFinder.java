/**
 * 
 */
package models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 * @author a.rizvi
 *
 */
public class NameFinder {

	/**
	 * Takes text as tokens and returns list of names in it
	 * @param tokens
	 * @param paragraph
	 * @return
	 */
	public static List<String> findNames(String[] tokens){
		InputStream modelIn = null;
		String name = "";
		Span nameSpans[] = null;
		List<String> names = new ArrayList<String>();

		try {
			modelIn = new FileInputStream("./opennlp_models/en-ner-person.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
			NameFinderME nameFinder = new NameFinderME(model);
			nameSpans = nameFinder.find(tokens);
			nameFinder.clearAdaptiveData();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				}
				catch (IOException e) {
				}
			}
		}
		
		for (Span s : nameSpans){
			for (int i = s.getStart(); i < s.getEnd(); i++){
				name += tokens[i];
			}
			names.add(name);
			name = "";
		}

		return names;
	}
}
