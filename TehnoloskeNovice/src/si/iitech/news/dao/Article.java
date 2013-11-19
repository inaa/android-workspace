package si.iitech.news.dao;

import java.util.List;

public class Article extends ArticlePreview {
	
	/**
	 * 
	 */
	private String author;
	private String data;
	private String imageStr;

	private List<MediaElement> images;
	private List<MediaElement> videos;
	private List<MediaElement> source;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getImageStr() {
		return imageStr;
	}

	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}

	public List<MediaElement> getImages() {
		return images;
	}

	public void setImages(List<MediaElement> images) {
		this.images = images;
	}

	public List<MediaElement> getVideos() {
		return videos;
	}

	public void setVideos(List<MediaElement> videos) {
		this.videos = videos;
	}

	public List<MediaElement> getSource() {
		return source;
	}

	public void setSource(List<MediaElement> source) {
		this.source = source;
	}
	
	

}
