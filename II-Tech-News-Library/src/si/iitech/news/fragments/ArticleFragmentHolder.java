package si.iitech.news.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;

public class ArticleFragmentHolder {

	private Fragment	fragment;
	private Drawable	drawable;
	private String		title;

	public static ArticleFragmentHolder createHolderForArticleListFragment(Context context, Drawable drawable, String title, String rest, int request,
			int type, int source) {
		Log.i("REST", rest + "/" + request + "/" + type + "/" + source);
		ArticleListFragment fragment = ArticleListFragment.newInstance(context, title, rest + "/" + request + "/" + type + "/" + source);
		return new ArticleFragmentHolder(drawable, fragment, title);
	}

	public static ArticleFragmentHolder createHolderForArticleSearchFragment(Context context, Drawable drawable, String title, String rest, int request,
			int type) {
		ArticleSearchFragment fragment = ArticleSearchFragment.newInstance(context, title, rest + "/" + request + "/" + type);
		return new ArticleFragmentHolder(drawable, fragment, title);
	}

	public ArticleFragmentHolder(Drawable drawable, Fragment fragment, String title) {
		this.fragment = fragment;
		this.drawable = drawable;
		this.title = title;
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public Fragment getFragment() {
		return fragment;
	}

	public String getTitle() {
		return title;
	}
}
