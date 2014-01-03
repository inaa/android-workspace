package si.iitech.news.activity;

import java.util.ArrayList;
import java.util.List;

import si.iitech.news.R;
import si.iitech.news.fragments.AboutFragment;
import si.iitech.news.fragments.ArticleListFragment;
import si.iitech.news.fragments.SearchFragment;
import android.app.ActionBar.LayoutParams;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class NewsActivity extends SlidingFragmentActivity implements OnClickListener {

	// Fragments
	private SearchFragment searchFragment;
	private Fragment aboutFragment;
	private SlidingMenu slidingMenu;

	private List<Fragment> fragments;

	private final String className = "NewsActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initSlidingMenu();
		createSlidingMenu();

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

	private void init() {

		searchFragment = new SearchFragment();
		aboutFragment = new AboutFragment();

		fragments = new ArrayList<Fragment>();

		fragments.add(searchFragment);
		fragments.add(aboutFragment);
	}

	public void startSearchFragment() {
		// startNews(resources.getString(R.string.rest_search),
		// resources.getString(R.string.rest_search_title), searchFragment);
	}

	public void startAboutFragment() {
		changeFragment(aboutFragment, R.id.ViewSwitcher);
		getSlidingMenu().toggle(true);
	}

	public void startSearch(View v) {
		startSearchFragment();
	}

	public void startAbout(View v) {
		Log.i("startAbout", "true");
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

	private void createSlidingMenu() {
		LinearLayout menuLayout = new LinearLayout(this);
		menuLayout.setOrientation(LinearLayout.VERTICAL);

		Drawable d = getResources().getDrawable(R.drawable.slo_tech);
		addElementOnMenu("Slo Tech", d, "http://95.85.43.192:8080/IITech/service/article/0", menuLayout);
		addElementOnMenu("Mobitel Tehnik", d, "http://95.85.43.192:8080/IITech/service/article/1", menuLayout);

		ScrollView s = new ScrollView(this);
		s.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		s.setBackgroundColor(getResources().getColor(R.color.bela));
		s.addView(menuLayout);

		setBehindContentView(s);

	}

	private void addElementOnMenu(String title, Drawable picture, String restPath, LinearLayout viewToAddOn) {
		Button button = new Button(this);
		button.setText(title);
		button.setCompoundDrawablesWithIntrinsicBounds(picture, null, null, null);
		button.setGravity(Gravity.START);
		button.setGravity(Gravity.CENTER_VERTICAL);
		button.setBackgroundResource(R.drawable.button_background_type_1);
		button.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		button.setOnClickListener(this);

		ArticleListFragment articleListFragment = ArticleListFragment.newInstance(this, title, restPath);
		button.setTag(articleListFragment);
		viewToAddOn.addView(button);
	}

	@Override
	public void onClick(View view) {
		if (view.getTag() instanceof ArticleListFragment) {
			changeFragment((ArticleListFragment) view.getTag(), R.id.ViewSwitcher);
		}

	}
}
