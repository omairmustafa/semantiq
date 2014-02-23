package models;

import java.io.*;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class WordDocReader {

	public static StringBuilder readDocument(File file) {
		
		File fileToProcess = null;
		WordExtractor extractor = null;
		StringBuilder documentText = new StringBuilder();
		
		try {

			fileToProcess = file;
			FileInputStream fis = new FileInputStream(fileToProcess.getAbsolutePath());
			HWPFDocument document = new HWPFDocument(fis);
			extractor = new WordExtractor(document);
			String [] fileData = extractor.getParagraphText();
			
			for(String paragraph: fileData){
				documentText.append(paragraph);
			}
			
//			for(int i=0; i<fileData.length; i++){
//				if(fileData[i] != null)
//					System.out.println(fileData[i]);
//			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return documentText;
	}
}
