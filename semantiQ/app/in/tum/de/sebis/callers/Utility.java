package in.tum.de.sebis.callers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Utility {
	// utility method
		public static String getStringFromDocument(Document doc) {
			try {
				DOMSource domSource = new DOMSource(doc);
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(domSource, result);

				return writer.toString();
			} catch (TransformerException ex) {
				ex.printStackTrace();
				return null;
			}
		}
		
		// utility function
		public static String getFileContents(String filename) throws IOException,
				FileNotFoundException {
			File file = new File(filename);
			StringBuilder contents = new StringBuilder();

			BufferedReader input = new BufferedReader(new FileReader(file));

			try {
				String line = null;

				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}

			return contents.toString();
		}
}
