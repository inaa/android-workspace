package si.iitech.news.adapter;

import java.util.Date;
import java.util.List;

import si.iitech.news.R;
import si.iitech.news.entity.ArticleEntity;
import si.iitech.news.entity.MediaElement;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ArticleAdapter extends BaseAdapter {

	private Context context;
	private List<ArticleEntity> list;

	public ArticleAdapter(Context context, List<ArticleEntity> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int index) {
		return list.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	private class ArticleHolder {
		TextView textViewTitle;
		TextView textViewDate;
		ImageView articleImage;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup view) {
		ArticleEntity article = list.get(index);
		ArticleHolder holder = null;
		String pictureUrl = "";

		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.article_preview_adapter, null);
			holder = new ArticleHolder();
			holder.textViewTitle = (TextView) convertView.findViewById(R.id.textView_title);
			holder.textViewDate = (TextView) convertView.findViewById(R.id.textView_date);
			holder.articleImage = (ImageView) convertView.findViewById(R.id.imageView_image);
			convertView.setTag(holder);
		} else {
			holder = (ArticleHolder) convertView.getTag();
		}
		
		holder.textViewTitle.setText(article.getTitle());
		Date date = article.getArticleDate();
		holder.textViewDate.setText(date.toString());

		List<MediaElement> images = article.getImages();
		if (images != null) {
			if (!images.isEmpty()) {
				pictureUrl = article.getImages().get(0).getHref();
			}
		}
		ImageLoader.getInstance().displayImage(pictureUrl, holder.articleImage);

		return convertView;
	}

}
