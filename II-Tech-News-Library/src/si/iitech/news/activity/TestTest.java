package si.iitech.news.activity;

import java.util.ArrayList;
import java.util.List;

import si.iitech.news.R;
import si.iitech.news.fragments.ArticleFragmentHolder;

public class TestTest extends ArticleListActivity {

	private final String	SERVER			= "http://92.37.114.68:8080/II-Tech/service/article";
	private final int		NEWS_TYPE		= 0;
	private final String	SEARCH			= "Iskanje";

	private final String	SLO_TECH_TITLE	= "Slo tech";
	private final int		SLO_TECH_SOURCE	= 0;

	@Override
	public List<ArticleFragmentHolder> getArticleListFragments() {
		List<ArticleFragmentHolder> holder = new ArrayList<ArticleFragmentHolder>();
		holder.add(ArticleFragmentHolder.createHolderForArticleListFragment(this, getResources().getDrawable(R.drawable.slo_tech), SLO_TECH_TITLE, SERVER,
				super.getRequestID(), NEWS_TYPE, SLO_TECH_SOURCE));
		return holder;
	}

	@Override
	public ArticleFragmentHolder getArticleSearchFragments() {
		return ArticleFragmentHolder.createHolderForArticleSearchFragment(this, getResources().getDrawable(R.drawable.slo_tech), SEARCH, SERVER,
				super.getRequestID(), NEWS_TYPE);
	}

}
