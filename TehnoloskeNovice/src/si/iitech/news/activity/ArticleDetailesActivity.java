package si.iitech.news.activity;

import java.util.Date;
import java.util.List;

import si.iiteam.tehnoloskenovice.R;
import si.iitech.dialog.ShowFullSizeImageDialog;
import si.iitech.news.dao.ArticleDAO;
import si.iitech.news.dao.MediaElement;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ArticleDetailesActivity extends FragmentActivity implements OnClickListener {

	private TextView textViewTitle;
	private TextView textViewAuthor;
	private TextView textViewDate;
	private TextView textViewData;
	private TextView textViewImagesTitle;
	private TextView textViewVideosTitle;
	private TextView textViewSourceTitle;
	private TextView textViewLinkToOriginalArticle;
	private LinearLayout linearLayoutImages;
	private LinearLayout linearLayoutVideos;
	private LinearLayout linearLayoutSource;
	private ArticleDAO article;
	private String href;
	private String title;

	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_details);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
		
		initView();
		displayArticle();
	}

	private void displayArticle() {
		Bundle bundle = this.getIntent().getExtras();
		article = (ArticleDAO) bundle.getSerializable("article");
		String titleString = article.getTitle();
		setTitle(titleString);
		displayNews(article);
	}

	private void initView() {
		textViewTitle = (TextView) findViewById(R.id.textView_title);
		textViewAuthor = (TextView) findViewById(R.id.textView_author);
		textViewDate = (TextView) findViewById(R.id.textView_date);
		textViewData = (TextView) findViewById(R.id.textView_data);
		linearLayoutImages = (LinearLayout) findViewById(R.id.linearLayout_images);
		textViewImagesTitle = (TextView) findViewById(R.id.textView_imagesTitle);
		linearLayoutVideos = (LinearLayout) findViewById(R.id.linearLayout_videos);
		textViewVideosTitle = (TextView) findViewById(R.id.TextView_videosTitle);
		linearLayoutSource = (LinearLayout) findViewById(R.id.linearLayout_source);
		textViewSourceTitle = (TextView) findViewById(R.id.textView_sourceTitle);
		textViewLinkToOriginalArticle = (TextView) findViewById(R.id.textView_linkToOriginal);

	}


	private void displayNews(ArticleDAO article) {
		Date date = article.getArticleDate();
		String author = article.getAuthor();
		
		
		//SE BO SPREMENILO NA PRAZEN STRING
		if(!author.equalsIgnoreCase("null")) {
			textViewAuthor.setText(author + ", ");
		} else {
			textViewAuthor.setVisibility(View.GONE);
		}
		title = article.getTitle();
		textViewTitle.setText(title);
		textViewDate.setText(date.toString());
		textViewData.setText(Html.fromHtml(article.getHtml()));
		textViewData.setMovementMethod(LinkMovementMethod.getInstance());
		href = article.getHref();
		String povezava = "<a href='" + href + "'> Povezava do èlanka </a>";
		textViewLinkToOriginalArticle.setLinksClickable(true);
		textViewLinkToOriginalArticle.setTextAppearance(this, R.style.NormalText);
		textViewLinkToOriginalArticle.setMovementMethod(LinkMovementMethod.getInstance());
		textViewLinkToOriginalArticle.setText(Html.fromHtml(povezava));
		displayImages(article.getImages());
		displayVideos(article.getVideos());
		displaySource(article.getSource());

	}

	private void displayImages(List<MediaElement> list) {
		if (list != null) {
			if(!list.isEmpty()) {
				textViewImagesTitle.setVisibility(View.VISIBLE);
				for (MediaElement m : list) {
					ImageView imageView = new ImageView(this);
					imageView.setAdjustViewBounds(true);
					imageView.setMinimumHeight(200);
					imageView.setMinimumWidth(200);
					imageView.setMaxWidth(200);
					imageView.setMaxHeight(200);
					imageView.setScaleType(ScaleType.CENTER_INSIDE);
					imageView.setPadding(5, 5, 5, 5);
					imageView.setClickable(true);
					imageView.setOnClickListener(this);
					imageView.setTag(m);
					linearLayoutImages.addView(imageView);
					imageLoader.displayImage(m.getHref(), imageView);
				}
			}
		}
	}

	private void displayVideos(List<MediaElement> list) {
		if (list != null) {
			if(!list.isEmpty()) {
				textViewVideosTitle.setVisibility(View.VISIBLE);
				for (int i = 0 ; i < list.size(); i++) {
					TextView textViewVideos = new TextView(this);
					String links = "<a href='" + list.get(i).getHref() + "'> Video" + (i + 1) + "</a>";
					textViewVideos.setLinksClickable(true);
					textViewVideos.setTextAppearance(this, R.style.NormalText);
					textViewVideos.setMovementMethod(LinkMovementMethod.getInstance());
					textViewVideos.setText(Html.fromHtml(links));
					linearLayoutVideos.addView(textViewVideos);
				}
			}
		}
	}

	private void displaySource(List<MediaElement> list) {
		if (list != null) {
			if(!list.isEmpty()) {
				textViewSourceTitle.setVisibility(View.VISIBLE);
				for (MediaElement m : list) {
					TextView textViewSource = new TextView(this);
					String links = "<a href='" + m.getHref() + "'> " + m.getTitle()
							+ "</a>";
					textViewSource.setLinksClickable(true);
					textViewSource.setTextAppearance(this, R.style.NormalText);
					textViewSource.setMovementMethod(LinkMovementMethod
							.getInstance());
					textViewSource.setText(Html.fromHtml(links));
					linearLayoutSource.addView(textViewSource);
					// Log.i("V source-ih!", m.getHref());
				}
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.share, menu);
	    return true;
	  }
	
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_item_share:
	      doShare();
	      break;
	    default:
	    	onBackPressed();
	      break;
	    }
	    return true;  
	  }

	  public void doShare() {
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, href);
		startActivity(Intent.createChooser(sharingIntent, "Delite èlanek"));
	  } 
	  
	@Override
	public void onClick(View v) {
		if (v.getTag() != null) {
//			Intent intent = new Intent(this,
//					FullScreenViewActivity.class);
//			Bundle b = new Bundle();
//			b.putSerializable("images", (Serializable) article.getImages());
//			intent.putExtras(b);
//			this.startActivity(intent);
//			new FullScreenImageAdapter(this, article.getImages());
			ShowFullSizeImageDialog.createDialog(this, article.getImages());
			
		}

	}

}
