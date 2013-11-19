package si.iitech.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import si.iitech.news.dao.ArticleDAO;
import si.iitech.news.dao.MediaElement;
import si.iitech.rest.RestCoreCrud;
import si.iitech.util.IITechDate;


public class ArticlesServiceImpl implements RestCoreCrud<ArticleDAO>
{

	@Override
	public List<ArticleDAO> convertToArticleList(JSONArray array) {
		List<ArticleDAO> list = new LinkedList<ArticleDAO>();
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject object = array.getJSONObject(i);
				
				list.add(convertToArticle(object));
			} catch (JSONException e) {
				// Log.i("converter", "error");
				return null;
			}
		}
		return list;
	}

//	@Override
//	public List<ArticlePreview> convertToArticlePreviewList(JSONArray array) {
//		List<ArticlePreview> list = new LinkedList<ArticlePreview>();
//		// Log.i("Json array", " " + array.length());
//		for (int i = 0; i < array.length(); i++) {
//			try {
//				JSONObject object = array.getJSONObject(i);
//				
//				list.add(convertToArticlePreview(object));
//			} catch (JSONException e) {
//				// Log.i("converter", "error");
//				return null;
//			}
//		}
//		return list;
//	}
//
	public ArticleDAO convertToArticle(JSONObject object) throws JSONException {
		ArticleDAO article = new ArticleDAO();

		article.setTitle(object.getString("title"));
		article.setType(object.getString("type"));
		article.setAuthor(object.getString("author"));
		long date = object.getLong("articleDate");
		article.setArticleDate(new IITechDate(date));
		article.setHtml(object.getString("html"));
//		Log.i("hrefOBJECT", object.getString("href"));
		article.setHref(object.getString("href"));
//		Log.i("href Article", article.getHref());

		if (object.isNull("images")) {
			article.setImages(null);
		} else {
			JSONArray arrayImages = object.getJSONArray("images");
			List<MediaElement> listImages = mediaElements(arrayImages);
			article.setImages(listImages);
		}

		if (object.isNull("videos")) {
			article.setVideos(null);
		} else {
			JSONArray arrayVideos = object.getJSONArray("videos");
			List<MediaElement> listVideos = mediaElements(arrayVideos);
			article.setVideos(listVideos);
		}

		if (object.isNull("source")) {
			article.setSource(null);
		} else {
			JSONArray arraySource = object.getJSONArray("source");
			List<MediaElement> listSource = mediaElements(arraySource);
			article.setSource(listSource);
		}
		
		// Log.i("singleArticle", article.toString());
		return article;
	}
//
//	public ArticlePreview convertToArticlePreview(JSONObject object)
//			throws JSONException {
//
//		MediaElement image = new MediaElement();
//		ArticlePreview preview = new ArticlePreview();
//		String href;
//		String title;
//		String date = object.getString("articleDate");
//		long dateValue = Long.parseLong(date);
//		if (object.isNull("image")) {
//			href = "http://djfinal.files.wordpress.com/2012/06/articles-djfinal.gif?w=368";
//			// Log.i("no image", href);
//			title = "Default picture";
//		} else {
//			JSONArray arrayImages = object.getJSONArray("images");
//			List<MediaElement> listImages = mediaElements(arrayImages);
//			MediaElement im = listImages.get(0);
//			href = im.getHref();
//			title = im.getTitle();
//		}
//		image.setHref(href);
//		image.setTitle(title);
//
//		preview.setLink(object.getString("id"));
//		preview.setDateTime(new IITechDate(dateValue));
//		preview.setImage(image);
//		preview.setTitle(object.getString("title"));
//
//		// Log.i("izpis", preview.toString());
//
//		return preview;
//	}
//
	public MediaElement convertToMediaElement(JSONObject object)
			throws JSONException {

		MediaElement mediaElement = new MediaElement();
		mediaElement.setHref(object.getString("href"));
		mediaElement.setTitle(object.getString("title"));

		return mediaElement;
	}

	public List<MediaElement> mediaElements(JSONArray array) {
		List<MediaElement> list = new LinkedList<MediaElement>();
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject a = array.getJSONObject(i);
				// Log.i("convertToArticle", a.toString());
				list.add(convertToMediaElement(a));
			} catch (JSONException e) {
				// Log.i("converter", "error");
				return null;
			}
		}
		return list;

	}

}
