package controllers;

import java.io.File;
import play.mvc.*;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import 

public class FileUpload extends Controller {

	/**
	 * @param args
	 */
	public static Result upload() {
		  MultipartFormData body = request().body().asMultipartFormData();
		  FilePart picture = body.getFile("file");
		  if (picture != null) {
		    String fileName = picture.getFilename();
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    return ok("File uploaded");
		  } else {
		    flash("error", "Missing file");
		    return ok(views.html.index.render("Upload error"));    
		  }
		}
}