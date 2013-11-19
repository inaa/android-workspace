package si.iitech.news.dao;

import java.io.Serializable;

public class MediaElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7438258521808184314L;
	private String href;
	private String title;
	
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
