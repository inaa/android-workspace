package si.iitech.news.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ArticleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String type;
    private Date articleDate;
    private String href;
    private String html;
    private String author;
    private List<MediaElement> images;
    private List<MediaElement> videos;
    private List<MediaElement> source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticleEntity)) {
            return false;
        }
        ArticleEntity other = (ArticleEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
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

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    

    @Override
    public String toString() {
        return "si.iitech.article.ArticleDAO[ id=" + id + " ]";
    }
}