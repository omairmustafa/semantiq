package models;

import java.text.DecimalFormat;
import java.util.HashMap;


import readibility.ReadablityIndexFactory;
import readibility.TextProcessor;


public class ResultProcessor {
	
	DecimalFormat df = new DecimalFormat("#.00");
  
	public Semantics getSemantics (String text){
		
		Semantics semantics = new Semantics();
		HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map = this.getIndexesResult(text);
		this.populateSemanticsForIndexes(map, semantics);
		this.populateSemantics(text, semantics);
		
		return semantics;
		
	}
	
	private void populateSemanticsForIndexes(HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map, Semantics semantics) {
		
		// {ARI, COLEMAN_LIAU, DALE_CHALL, SMOG, FLESCH_KINCAID}
		semantics.setAutomatedReadibilityIndex(map.get(ReadablityIndexFactory.ReadablityIndex.ARI));
		semantics.setColemanLiauIndex(map.get(ReadablityIndexFactory.ReadablityIndex.COLEMAN_LIAU));
		semantics.setDaleCchallIndex((map.get(ReadablityIndexFactory.ReadablityIndex.DALE_CHALL)));
		semantics.setsMOGIndex((map.get(ReadablityIndexFactory.ReadablityIndex.SMOG)));
		semantics.setFleschKincaidIndex((map.get(ReadablityIndexFactory.ReadablityIndex.FLESCH_KINCAID)));
	}
	
	private void populateSemantics(String text, Semantics semantics) {
		TextProcessor processor = new TextProcessor(text);
		
		semantics.setText(processor.getText());
		semantics.setNames(processor.getNames().toString());
		semantics.setNumOfSentences(processor.getSentences().size());
		semantics.setNumOfWords(processor.getTokens().length);
	}
	 private HashMap<ReadablityIndexFactory.ReadablityIndex, Double> getIndexesResult(String text){
		 
			ReadablityIndexFactory indexFactory = new ReadablityIndexFactory();
			HashMap<ReadablityIndexFactory.ReadablityIndex, Double> map = new HashMap<ReadablityIndexFactory.ReadablityIndex, Double>();
			for (ReadablityIndexFactory.ReadablityIndex index : ReadablityIndexFactory.ReadablityIndex.values()) {
				double result =  indexFactory.getReadablityIndexRating(index, text);
				map.put(index, result);
				
			}
			
			return map;
	 }
}
