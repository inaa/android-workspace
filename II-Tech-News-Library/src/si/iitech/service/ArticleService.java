package si.iitech.service;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import si.iitech.news.entity.ArticleEntity;
import si.iitech.news.entity.MediaElement;
import si.iitech.rest.RestCoreCrud;
import si.iitech.util.IITechDate;

public class ArticleService implements RestCoreCrud<ArticleEntity> {

	private final String className = "ArticleService";

	/**
	 * Method that converts JSONArray from server to List of ArticleEntity
	 */
	@Override
	public List<ArticleEntity> convertToArticleList(JSONArray array) {
		List<ArticleEntity> list = new LinkedList<ArticleEntity>();
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject object = array.getJSONObject(i);
				list.add(convertToArticle(object));
			} catch (JSONException e) {
				Log.i(className, e.toString());
				return list;
			}
		}
		return list;
	}

	public ArticleEntity convertToArticle(JSONObject object) throws JSONException {
		ArticleEntity article = new ArticleEntity();

		article.setTitle(object.getString("title"));
		article.setType(object.getString("type"));
		article.setAuthor(object.getString("author"));
		long date = object.getLong("articleDate");
		article.setArticleDate(new IITechDate(date));
		article.setHtml(object.getString("html"));
		article.setHref(object.getString("href"));

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

	public MediaElement convertToMediaElement(JSONObject object) throws JSONException {
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
