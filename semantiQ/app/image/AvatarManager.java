package image;

import java.util.List;

public class AvatarManager {

	public  List<String> getImageUrls(String keyword) {
	
		keyword = keyword.replaceAll(" ", "%20");
		ImagesLoader loader = new GoogleImagesLoader();
		return loader.getImageUrls(keyword);
	}
 
}
