package si.iitech.news.activity;

import si.iiteam.tehnoloskenovice.R;
import si.iitech.news.fragments.AboutFragment;
import si.iitech.news.fragments.ArticleListFragment;
import si.iitech.news.fragments.SearchFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class TehnoloskeNovice extends SlidingFragmentActivity {

	// Fragments
	private ArticleListFragment sloTechFragment;
	private ArticleListFragment mobitelTehnikFragment;
	private ArticleListFragment racunalniskeNoviceFragment;
	private ArticleListFragment zadnjeNoviceFragment;
	private ArticleListFragment mobiNoviceFragment;
	private ArticleListFragment sloAndroidFragment;
	private SearchFragment searchFragment;
	private Fragment aboutFragment;
	private SlidingMenu slidingMenu;
	private Resources resources;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		// set the content view
		setContentView(R.layout.main);
		setBehindContentView(R.layout.menu);

		slidingMenu = getSlidingMenu();
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidth(15);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffset(200);
		slidingMenu.setFadeDegree(0.35f);

		// Širina ozadja
		// slidingMenu.setBehindWidth(100);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);

		resources = this.getResources();

		startZadnjeNoviceFragment();

	}

	private void init() {
		sloTechFragment = new ArticleListFragment();
		mobitelTehnikFragment = new ArticleListFragment();
		racunalniskeNoviceFragment = new ArticleListFragment();
		zadnjeNoviceFragment = new ArticleListFragment();
		mobiNoviceFragment = new ArticleListFragment();
		sloAndroidFragment = new ArticleListFragment();
		searchFragment = new SearchFragment();
		aboutFragment = new AboutFragment();
	}

	public void startNews(final String rest, final String title,
			final Fragment newsFragment) {
		if (!newsFragment.isVisible()) {
			Bundle bundle = new Bundle();
			bundle.putString(resources.getString(R.string.bundle_rest), rest);
			bundle.putString(resources.getString(R.string.bundle_title), title);
			newsFragment.setArguments(bundle);
			changeFragment(newsFragment, R.id.ViewSwitcher);
			getSlidingMenu().toggle(true);
		} else {
			getSlidingMenu().toggle(true);
		}
	}

	public void startSloTechFragment() {
		startNews(resources.getString(R.string.rest_slo_tech),
				resources.getString(R.string.rest_slo_tech_title),
				sloTechFragment);
	}

	public void startMobitelTehnikFragment() {
		startNews(resources.getString(R.string.rest_mobitel_tehnik),
				resources.getString(R.string.rest_mobitel_tehnik_title),
				mobitelTehnikFragment);
	}

	public void startRacunalniskeNoviceFragment() {
		startNews(resources.getString(R.string.rest_racunalniske_novice),
				resources.getString(R.string.rest_racunalniske_novice_title),
				racunalniskeNoviceFragment);
	}

	public void startZadnjeNoviceFragment() {
		startNews(resources.getString(R.string.rest_zadnje_novice),
				resources.getString(R.string.rest_zadnje_novice_title),
				zadnjeNoviceFragment);
	}
	
	public void startMobiNoviceFragment() {
		startNews(resources.getString(R.string.rest_mobi_novice),
				resources.getString(R.string.rest_mobi_novice_title),
				mobiNoviceFragment);
	}
	
	public void startSloAndroidFragment() {
		startNews(resources.getString(R.string.rest_slo_android),
				resources.getString(R.string.rest_slo_android_title),
				sloAndroidFragment);
	}

	public void startSearchFragment() {
		startNews(resources.getString(R.string.rest_search),
				resources.getString(R.string.rest_search_title), searchFragment);
	}
	
	public void startAboutFragment() {
		changeFragment(aboutFragment, R.id.ViewSwitcher);
		Log.i("startAboutFragment", "");
		getSlidingMenu().toggle(true);
	}

	// Nevem ali moramo vedno znova kreirat fragment. Force close nastaja èe
	// nena, treba raziskat zadevo

	public void startSloTech(View v) {
		startSloTechFragment();
	}

	public void startMobitelTehnik(View v) {
		startMobitelTehnikFragment();
	}

	public void startRacunalniskeNovice(View v) {
		startRacunalniskeNoviceFragment();
	}

	public void startZadnjeNovice(View v) {
		startZadnjeNoviceFragment();
	}
	
	public void startMobiNovice(View v) {
		startMobiNoviceFragment();
	}
	
	public void startSloAndroid(View v) {
		startSloAndroidFragment();
	}
	
//
//	public void startNastavitve(View v) {
//
//	}

	public void startSearch(View v) {
		startSearchFragment();
	}
	
	public void startAbout(View v) {
		Log.i("startAbout", "true");
		startAboutFragment();
	}

	protected void changeFragment(Fragment fragment, int fragmenentLayout) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(fragmenentLayout, fragment);
		Log.i("changeFragment", "true");
		// fragmentTransaction
		// .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.commit();
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

}
