package si.iitech.news.dao;

import java.io.Serializable;

import si.iitech.util.IITechDate;

public class ArticlePreview implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String link;
	private String title;
	private MediaElement image;
	private IITechDate dateTime;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public MediaElement getImage() {
		return image;
	}
	public void setImage(MediaElement image) {
		this.image = image;
	}
	public IITechDate getDateTime() {
		return dateTime;
	}
	public void setDateTime(IITechDate dateTime) {
		this.dateTime = dateTime;
	}
	
	@Override
	public String toString() {
		return "ArticlePreview [id=" + link + ", title=" + title + ", image="
				+ image + ", dateTime=" + dateTime + "]";
	}	
	
	
	
}
