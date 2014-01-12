package si.iitech.news.activity;

import java.util.ArrayList;
import java.util.List;

import si.iitech.news.R;
import si.iitech.news.fragments.ArticleFragmentHolder;

public class TestTest extends ArticleListActivity {

	@Override
	public List<ArticleFragmentHolder> getArticleListFragments() {
		List<ArticleFragmentHolder> holder = new ArrayList<ArticleFragmentHolder>();
		holder.add(
				ArticleFragmentHolder.createHolderForArticleListFragment(
						this, 
						getResources().getDrawable(R.drawable.slo_tech), 
						"SLO TECH", 
						"http://95.85.43.192:8080/IITech/service/article/0",
						"",
						"",
						""));
		return holder;
	}

	@Override
	public ArticleFragmentHolder getArticleSearchFragments() {
		return ArticleFragmentHolder.createHolderForArticleSearchFragment(
				this, 
				getResources().getDrawable(R.drawable.slo_tech), 
				"SEARCH", 
				"http://95.85.43.192:8080/IITech/service/article",
				"",
				"");
	}

}
