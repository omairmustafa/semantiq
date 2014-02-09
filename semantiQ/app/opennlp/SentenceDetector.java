package opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

import com.google.common.io.Files;

public class SentenceDetector {
	
	public static List<String> detectSentences(String paragraph) throws InvalidFormatException,
	IOException {
		
		//Always start with a model, a model is learned from training data
		InputStream inputStream = new FileInputStream("./opennlp_models/en-sent.bin");
		
		SentenceModel model = new SentenceModel(inputStream);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

 		String sentences[] = sdetector.sentDetect(paragraph);

		
		List<String> list = new ArrayList<>();
		for (String str : sentences)
			list.add(str);
		
		inputStream.close();
		
		return list;
		
	}
}
