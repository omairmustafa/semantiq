package controllers;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import opennlp.tools.util.InvalidFormatException;

import models.NameFinder;
import models.ResultProcessor;
import models.Semantics;
import models.SentenceDetector;
import models.Stopwords;
import models.TextInput;
import models.TextSummarizer;
import models.Tokenizer;
import models.WordDocReader;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Controller;
import play.mvc.Result;
import readibility.EnglishSyllableCounter;
import readibility.TextProcessor;
import scala.collection.immutable.Page;
import views.html.*;

public class Application extends Controller {

	public static Result index() throws InvalidFormatException, IOException {
		return redirect(routes.Application.home());
	}

	public static Result home() {
		return ok(views.html.home.render());
	}

	public static Result semantiq() {
		return ok(views.html.semantiq.render(Form.form(TextInput.class)));
	}

	public static Result textInput() {

		//Take form input
		TextInput textInput = new TextInput();
		Form<TextInput> form = Form.form(TextInput.class).bindFromRequest();
		Semantics semantics = null;
		ResultProcessor processor = new ResultProcessor();
		
		if(form.hasErrors()) {
			return ok();
		} 
		else {
			textInput = form.get();

			try {
				semantics = processor.getSemantics(textInput.getText());
				
				
				//Summary under test
				TextSummarizer summarizer = new TextSummarizer();
				summarizer.summarise(textInput.getText(), 7);
				
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return ok((semantics != null) ? views.html.result.render(semantics) : views.html.index.render("Oops! Unable to process your text"));

		}
	}

	public static Result aboutus(){
		return ok(views.html.aboutus.render());
	}

	public static Result contact(){
		return ok(views.html.contact.render());
	}

	public static Result terms(){
		return ok(views.html.terms.render());
	}

	public static Result privacy(){
		return ok(views.html.privacy.render());
	}

	public static Result uploadFile() {
		Semantics semantics = null;
		ResultProcessor processor = new ResultProcessor();
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart filePart = body.getFile("filePart");

		if (filePart != null) {
			String fileName = filePart.getFilename();
			String contentType = filePart.getContentType();  
			File file = filePart.getFile();

			StringBuilder fileContents = new StringBuilder((int)file.length());
			
			System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>> " + contentType);
			



			String lineSeparator = System.getProperty("line.separator");

			try {
				if (contentType.equals("application/msword")){
					fileContents = WordDocReader.readDocument(file);
				}//if
				else{
					Scanner scanner = new Scanner(file);
					while(scanner.hasNextLine()) {        
						fileContents.append(scanner.nextLine() + lineSeparator);
					}//while
					scanner.close();
				}//else
				
				semantics = processor.getSemantics(fileContents.toString());
				
				return ok((semantics != null) ? views.html.result.render(semantics) : views.html.index.render("Oops! Unable to process your text"));
			} 
			catch (Exception e) {
				e.printStackTrace();
				return ok(views.html.index.render("Oops! Unable to process your text"));
			} 
//			finally {
//				scanner.close();
//			}
		} 
		else {
			flash("error", "Missing file");
			return redirect(routes.Application.index());    
		}
	}

	//
	//	public static Semantics processText(String text) throws InvalidFormatException, IOException {
	//		TextInput textInput = new TextInput();
	//		Form<TextInput> form = Form.form(TextInput.class).bindFromRequest();
	//		ReadibilityIndexes indexes = new ReadibilityIndexes();
	//		Stopwords swords = new Stopwords();
	//		int syllableCount = 0;
	//		
	//		List<String> sentenceList = new ArrayList();
	//		String tokenString = "";
	//		String[] tokens;
	//		Semantics semantics = null;
	//		String paragraph = "";
	//		paragraph = text;
	//
	//		System.out.println(">>>>>>>>>" + paragraph);
	//		
	//		if(paragraph.length() > 0) {
	//			semantics = new Semantics();
	//			try {
	//				sentenceList = SentenceDetector.detectSentences(paragraph);
	//			} catch (InvalidFormatException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			tokens = Tokenizer.tokenize(paragraph);
	//
	//			for (String str : tokens){
	//				tokenString += "|" + str;
	//			}
	//
	//			for (String str : tokens){
	//				syllableCount += EnglishSyllableCounter.countSyllables(str);
	//			}
	//			
	//			semantics.setParagraph(paragraph);
	//			semantics.setNumOfSentences(sentenceList.size());
	//			semantics.setNumOfWords(tokens.length);
	//			//        		semantics.setSyllableCount(EnglishSyllableCounter.countSyllables("Extreme"));
	//			semantics.setARI(indexes.calculateARI(paragraph, tokens.length, sentenceList.size()));
	//			semantics.setFKI(indexes.calculateFleschKincaidIndex(tokens.length, sentenceList.size(), syllableCount));
	//			semantics.setCLI(indexes.calculateColemanLiauIndex(paragraph.replaceAll("[^\\w]", "").length(), tokens.length, sentenceList.size()));
//				semantics.setNames(NameFinder.findNames(tokens).toString());
	//			semantics.setDCI(indexes.calculateDaleChallIndex(tokens, tokens.length, sentenceList.size()));
	//			semantics.setSMOG(calculateSMOG(paragraph));
	//		}
	//
	//		return semantics;
	//	}
}
