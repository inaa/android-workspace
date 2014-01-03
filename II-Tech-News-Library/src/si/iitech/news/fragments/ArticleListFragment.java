package si.iitech.news.fragments;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ArticleListFragment extends PullToRefreshListFragment implements LoaderCallbacks<RestResponce>, OnRefreshListener<ListView> {

	private ArticleService service;
	private List<ArticleEntity> list;
	private ArticleAdapter noviceAdapter;
	private Resources resources;
	private String title;
	private final String dateTitle = "_DATE_TITLE";
	private PullToRefreshListView listView;
	private final long saveTime = 3600000L;

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
		resources = getResources();

		title = getArguments().getString(resources.getString(R.string.bundle_title));
		getActivity().getActionBar().setTitle(title);
		init();
		// loadRest();
		retriveData();
	}

	@SuppressWarnings("unchecked")
	private void retriveData() {
		Long currentTime = System.currentTimeMillis();
		Log.i("currentTime", currentTime + "");
		Long time = deserialize(Long.class, title + dateTitle);
		Log.i("currentTime", time + "");
		if (null != time) {
			if (currentTime - time > saveTime) {
				list = deserialize(List.class, title);
				if (null != list) {
					displayNews();
				}
				loadRest();
			} else {
				list = deserialize(List.class, title);
				displayNews();
			}
		} else {
			loadRest();
		}

	}

	private void loadRest() {
		// ZAÈETEK :D
		String REST_PATH = getArguments().getString(resources.getString(R.string.bundle_rest));
		Uri url = Uri.parse(REST_PATH);
		Bundle args = new Bundle();
		args.putParcelable(RestCall.URI_REST, url);
		// articles = deserialize(ArticlePreview.class, fileName);
		getLoaderManager().initLoader(RestCall.NALOZI_REST, args, this);

	}

	private void init() {
		service = new ArticleService();
		listView = this.getPullToRefreshListView();
		listView.setOnRefreshListener(this);
	}

	@Override
	public Loader<RestResponce> onCreateLoader(int id, Bundle args) {
		// VMESNA STOPNJA :D
		Uri url = args.getParcelable(RestCall.URI_REST);
		return new RestCall(this.getActivity(), RestEnum.GET, url, "");
	}

	@Override
	public void onLoadFinished(Loader<RestResponce> arg0, RestResponce responce) {
		listView.onRefreshComplete();
		try {
			if (responce.getStatusCode() == RestCall.httpStatusOK) {
				String data = responce.getData();
				// Log.i("DATA", data);
				// Log.i("DATA", data.getBytes().length + "");

				JSONArray array = new JSONArray(data);
				list = service.convertToArticleList(array);
				serialize(list, title);
				serialize(System.currentTimeMillis(), title + dateTitle);
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
		b.putSerializable("article", article);
		intent.putExtras(b);
		getActivity().startActivity(intent);
	}

	private void displayNews() {
		noviceAdapter = new ArticleAdapter(getActivity().getApplicationContext(), list);
		this.setListAdapter(noviceAdapter);

	}

	@Override
	public void onLoaderReset(Loader<RestResponce> arg0) {
		// TODO Auto-generated method stub

	}

	protected void serialize(Object o, String fileName) {
		try {
			FileOutputStream fos = getActivity().getApplicationContext().openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(o);
			os.close();
		} catch (IOException e) {
			// Log.i("serialize", e.toString());
		}
	}

	protected <T> T deserialize(Class<T> clazz, String fileName) {
		try {
			FileInputStream fis = getActivity().getApplicationContext().openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			T articleList = clazz.cast(is.readObject());
			is.close();
			return articleList;
		} catch (IOException e) {
			// Log.i("deserialize1", e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			// Log.i("deserialize2", e.toString());
			return null;
		}
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

}
