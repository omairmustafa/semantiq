package controllers;

//import models.SentenceDetector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.util.InvalidFormatException;

import models.NameFinder;
import models.SentenceDetector;
import models.Stopwords;
import models.Tokenizer;
import play.mvc.Controller;
import play.mvc.Result;
import scala.collection.immutable.Page;

public class Application extends Controller {
    
    public static Result index() throws InvalidFormatException, IOException {
    	String paragraph = "Pierre Abraham, 61 years old, will join the board as a nonexecutive director replacing John on Nov. 29. Ali will not let that happen";
    	Stopwords swords = new Stopwords();
    	List list = new ArrayList();
    	
    	list = SentenceDetector.detectSentences(paragraph);
    	String[] tokens = Tokenizer.tokenize(paragraph);
    	String tokenString = "";
    	for (String str : tokens){
    		tokenString += "|" + str;
    	}
    	
    	return ok("Paragraph: " + paragraph + "\nSentences: " + Integer.toString(list.size()) + 
    			"\nTokens: " + tokenString + "\nNames: " + NameFinder.findNames(tokens) + 
    			"\nStopwords detected: " + Stopwords.analyzeString(tokens));
//   	return redirect(routes.Application.home());
    }

    public static Result home() {
    	return ok(views.html.home.render());
	}
    
    public static Result semantiq() {
    	return ok(views.html.semantiq.render());
	}
    
    public static Result aboutus(){
    	return ok(views.html.aboutus.render());
    }
}
