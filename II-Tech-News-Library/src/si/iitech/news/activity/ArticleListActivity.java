package si.iitech.news.activity;

import java.util.List;
import java.util.Random;

import si.iitech.news.R;
import si.iitech.news.fragments.ArticleFragmentHolder;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class ArticleListActivity extends SlidingFragmentActivity implements OnClickListener {

	// Fragments
	private Fragment	aboutFragment;

	private SlidingMenu	slidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_list_activity);

		initSlidingMenu();
		addFragmentsOnSlidingMenu();
		initActionBar();
	}

	private void initActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
	}

	private void initSlidingMenu() {
		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidth(15);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffset(100);
		slidingMenu.setFadeDegree(0.35f);
	}

	public void startAboutFragment() {
		changeFragment(aboutFragment, R.id.ViewSwitcher);
		getSlidingMenu().toggle(true);
	}

	public void startAbout(View v) {
		startAboutFragment();
	}

	protected void changeFragment(Fragment fragment, int fragmenentLayout) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(fragmenentLayout, fragment);
		fragmentTransaction.commit();
		getSlidingMenu().toggle(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			getSlidingMenu().toggle(true);
			return true;
		}
		return onOptionsItemSelected(item);
	}

	private void addFragmentsOnSlidingMenu() {

		LinearLayout menuLayout = new LinearLayout(this);
		menuLayout.setOrientation(LinearLayout.VERTICAL);

		List<ArticleFragmentHolder> articleListFragments = getArticleListFragments();

		if (articleListFragments != null) {
			for (ArticleFragmentHolder holder : articleListFragments) {
				addFragmentOnMenu(holder, menuLayout);
			}
		}

		ArticleFragmentHolder search = getArticleSearchFragments();

		addFragmentOnMenu(search, menuLayout);

		ScrollView s = new ScrollView(this);
		s.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		s.setBackgroundColor(getResources().getColor(R.color.bela));
		s.addView(menuLayout);

		setBehindContentView(s);
	}

	public abstract List<ArticleFragmentHolder> getArticleListFragments();

	public abstract ArticleFragmentHolder getArticleSearchFragments();

	private void addFragmentOnMenu(ArticleFragmentHolder holder, LinearLayout viewToAddOn) {

		if (holder != null) {
			Button button = new Button(this);
			button.setText(holder.getTitle());
			button.setCompoundDrawablesWithIntrinsicBounds(holder.getDrawable(), null, null, null);
			button.setGravity(Gravity.START);
			button.setGravity(Gravity.CENTER_VERTICAL);
			button.setBackgroundResource(R.drawable.button_background_type_1);
			button.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			button.setOnClickListener(this);
			button.setTag(holder.getFragment());
			viewToAddOn.addView(button);
		}
	}

	@Override
	public void onClick(View view) {
		if (view.getTag() instanceof Fragment) {
			changeFragment((Fragment) view.getTag(), R.id.ViewSwitcher);
		}
	}

	public int getRequestID() {
		String idKey = "id";
		String sharedPreferencesKey = "news";

		SharedPreferences prefs = this.getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE);
		int id = prefs.getInt(idKey, 0);
		if (id == 0) {
			id = new Random().nextInt(100000) + 1;
			prefs.edit().putInt(idKey, id).commit();
		}
		return id;

	}
}
