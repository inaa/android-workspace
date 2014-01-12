package si.iitech.news.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

public class ArticleFragmentHolder {

	private Fragment	fragment;
	private Drawable	drawable;
	private String		title;

	public static ArticleFragmentHolder createHolderForArticleListFragment(Context context, Drawable drawable, String title, String rest, String request, String type, String source) {
		
		
		
		ArticleListFragment fragment = ArticleListFragment.newInstance(context, title, rest);
		return new ArticleFragmentHolder(drawable, fragment, title);
	}

	public static ArticleFragmentHolder createHolderForArticleSearchFragment(Context context, Drawable drawable, String title, String rest, String request, String type) {
		
		
		ArticleSearchFragment fragment = ArticleSearchFragment.newInstance(context, title, rest);
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
