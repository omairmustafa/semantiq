package controllers;

import in.tum.de.sebis.callers.URLSummarizer;
import in.tum.de.sebis.callers.URLTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import opennlp.tools.util.InvalidFormatException;

import models.ResultProcessor;
import models.Semantics;
import models.TextInput;
import models.TextSummarizer;
import models.URLInput;
import models.WordDocReader;
import play.data.Form;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

//	public static Result index() throws InvalidFormatException, IOException {
//		return redirect(routes.Application.home());
//	}

	public static Result home() {
		return ok(views.html.semantiq.render(Form.form(TextInput.class), Form.form(URLInput.class)));
	}

	public static Result semantiq() {
		return ok(views.html.semantiq.render(Form.form(TextInput.class), Form.form(URLInput.class)));
	}
	
	public static Result urlInput() {

		//Take form input
		URLInput urlInput = new URLInput();
		Form<URLInput> form = Form.form(URLInput.class).bindFromRequest();
		Semantics semantics = null;
		ResultProcessor processor = new ResultProcessor();
		
		if(form.hasErrors()) {
			return ok(views.html.error.render("Oops! Unable to submit URL"));
		} 
		else {
			urlInput = form.get();

			try {
				
				URLSummarizer http = new URLSummarizer();
				semantics = processor.getSemantics(URLTextExtractor.extractTextFromURL(urlInput.getUrl()));
				semantics.setSummary(http.getUrlSummary(urlInput.getUrl()));
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return ok((semantics != null) ? views.html.result.render(semantics) : views.html.error.render("Oops! Unable to process your text"));
		}
		
	}

	public static Result textInput() {

		//Take form input
		TextInput textInput = new TextInput();
		Form<TextInput> form = Form.form(TextInput.class).bindFromRequest();
		Semantics semantics = null;
		ResultProcessor processor = new ResultProcessor();
		
		if(form.hasErrors()) {
			return ok(views.html.error.render("Oops! Unable to submit your text"));
		} 
		else {
			textInput = form.get();

			try {
				semantics = processor.getSemantics(textInput.getText());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			return ok((semantics != null) ? views.html.result.render(semantics) : views.html.error.render("Oops! Unable to process your text"));

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
//			String fileName = filePart.getFilename();
			String contentType = filePart.getContentType();  
			File file = filePart.getFile();

			StringBuilder fileContents = new StringBuilder((int)file.length());
			

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
				
				return ok((semantics != null) ? views.html.result.render(semantics) : views.html.error.render("Oops! Unable to process your text"));
			} 
			catch (Exception e) {
				e.printStackTrace();
				return ok(views.html.error.render("Oops! Unable to process your text"));
			} 
//			finally {
//				scanner.close();
//			}
		} 
		else {
			flash("error", "Missing file");
			return ok(views.html.error.render("Missing file"));
		}
	}

}
