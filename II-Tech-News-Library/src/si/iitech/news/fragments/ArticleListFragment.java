package si.iitech.news.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import si.iitech.news.R;
import si.iitech.news.activity.ArticleDetailesActivity;
import si.iitech.news.adapter.ArticleAdapter;
import si.iitech.news.entity.ArticleEntity;
import si.iitech.rest.RestCall;
import si.iitech.rest.RestEnum;
import si.iitech.rest.RestResponce;
import si.iitech.service.ArticleService;
import si.iitech.util.ArticleValues;
import si.iitech.util.Serialize;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ArticleListFragment extends PullToRefreshListFragment implements LoaderCallbacks<RestResponce>, OnRefreshListener<ListView> {

	private ArticleService			service;
	private List<ArticleEntity>		list;
	private ArticleAdapter			articleAdapter;
	private Resources				resources;
	private String					title;
	private PullToRefreshListView	listView;

	public static final ArticleListFragment newInstance(Context context, String title, String restPath) {
		Bundle bundle = new Bundle();
		bundle.putString(context.getResources().getString(R.string.bundle_title, title), title);
		bundle.putString(context.getResources().getString(R.string.bundle_rest), restPath);
		ArticleListFragment newsFragment = new ArticleListFragment();
		newsFragment.setArguments(bundle);
		return newsFragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
		retriveData();
	}

	private void init() {
		resources = getResources();
		title = getArguments().getString(resources.getString(R.string.bundle_title));
		getActivity().getActionBar().setTitle(title);
		service = new ArticleService();
		listView = this.getPullToRefreshListView();
		listView.setOnRefreshListener(this);
		listView.setShowIndicator(true);
	}

	@SuppressWarnings("unchecked")
	private void retriveData() {
		Long currentTime = System.currentTimeMillis();
		Long time = Serialize.deserialize(getActivity(), Long.class, title, Serialize.prefixTime);
		if (null != time) {
			if (currentTime - time > ArticleValues.TIME_ONE_MINUTE) {
				list = Serialize.deserialize(getActivity(), List.class, title, Serialize.prefixList);
				if (null != list) {
					displayNews();
				}
				loadRest();
			} else {
				list = Serialize.deserialize(getActivity(), List.class, title, Serialize.prefixList);
				displayNews();
			}
		} else {
			loadRest();
		}
	}

	private void loadRest() {
		String rest = getArguments().getString(resources.getString(R.string.bundle_rest));
		Uri url = Uri.parse(rest);
		Bundle args = new Bundle();
		args.putParcelable(RestCall.URI_REST, url);
		getLoaderManager().initLoader(RestCall.NALOZI_REST, args, this);
	}

	@Override
	public Loader<RestResponce> onCreateLoader(int id, Bundle args) {
		Uri url = args.getParcelable(RestCall.URI_REST);
		return new RestCall(this.getActivity(), RestEnum.GET, url, "");
	}

	@Override
	public void onLoadFinished(Loader<RestResponce> arg0, RestResponce responce) {
		listView.onRefreshComplete();
		try {
			if (responce.getStatusCode() == RestCall.httpStatusOK) {
				String data = responce.getData();
				JSONArray array = new JSONArray(data);
				list = service.convertToArticleList(array);
				Serialize.serialize(getActivity(), list, title, Serialize.prefixList);
				Serialize.serialize(getActivity(), System.currentTimeMillis(), title, Serialize.prefixTime);
				displayNews();
			} else {
				Toast.makeText(this.getActivity(), this.getActivity().getResources().getString(R.string.opozorilo_ni_podatkov), Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		getLoaderManager().destroyLoader(RestCall.NALOZI_REST);

	}

	private void openArticleDetails(ArticleEntity article) {
		Intent intent = new Intent(this.getActivity(), ArticleDetailesActivity.class);
		Bundle b = new Bundle();
		b.putSerializable(ArticleValues.INTENT_EXTRA_ARTICLE, article);
		intent.putExtras(b);
		getActivity().startActivity(intent);
	}

	private void displayNews() {
		articleAdapter = new ArticleAdapter(getActivity().getApplicationContext(), list);
		this.setListAdapter(articleAdapter);

	}

	@Override
	public void onLoaderReset(Loader<RestResponce> arg0) {
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ArticleEntity article = list.get(position - 1);
		openArticleDetails(article);
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		loadRest();
	}

	public String getTitle() {
		return title;
	}

}
