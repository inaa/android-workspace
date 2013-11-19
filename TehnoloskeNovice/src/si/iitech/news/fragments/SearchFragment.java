package si.iitech.news.fragments;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import si.iiteam.tehnoloskenovice.R;
import si.iitech.news.activity.ArticleDetailesActivity;
import si.iitech.news.adapter.ArticlesAdapter;
import si.iitech.news.dao.ArticleDAO;
import si.iitech.rest.RestCall;
import si.iitech.rest.RestEnum;
import si.iitech.rest.RestResponce;
import si.iitech.service.impl.ArticlesServiceImpl;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchFragment extends Fragment implements
		LoaderCallbacks<RestResponce>, OnClickListener, OnItemClickListener {

	private ArticlesServiceImpl service;
	private List<ArticleDAO> list;
	private EditText text;
	private Button add;
	private Resources resources;
	private String title;
	private ArticlesAdapter noviceAdapter;
	private ListView listViewResult;
	private Activity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.search, container, false);
		add = (Button) v.findViewById(R.id.add);
		text = (EditText) v.findViewById(R.id.text);
		
		add.setOnClickListener(this);
		listViewResult = (ListView) v.findViewById(R.id.listView_result);
		listViewResult.setOnItemClickListener(this);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		resources = getResources();
		activity = getActivity();
		title = getArguments().getString(
				resources.getString(R.string.bundle_title));
		activity.getActionBar().setTitle(title);
		service = new ArticlesServiceImpl();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		text.setText("");
	}

	private void doSearch(String query) {
		JSONArray jsonArray = new JSONArray();
		String[] searchParameters = query.split("\\s+");
		for (String parameter : searchParameters) {
			jsonArray.put(parameter);
		}
		Log.i("JSON", jsonArray.toString());
		loadRest(jsonArray.toString());
	}

	private void loadRest(final String json) {
		String REST_PATH = getArguments().getString(
				resources.getString(R.string.bundle_rest));
		Uri url = Uri.parse(REST_PATH);
		Bundle args = new Bundle();
		args.putParcelable(RestCall.URI_REST, url);
		args.putString(RestCall.JSON_REST, json);
		// articles = deserialize(ArticlePreview.class, fileName);
		getLoaderManager().initLoader(RestCall.NALOZI_REST, args, this);
	}

	@Override
	public Loader<RestResponce> onCreateLoader(int arg0, Bundle args) {
		Uri url = args.getParcelable(RestCall.URI_REST);
		String json = args.getString(RestCall.JSON_REST);
		return new RestCall(this.getActivity(), RestEnum.POST, url, json);
	}

	@Override
	public void onLoadFinished(Loader<RestResponce> arg0, RestResponce responce) {
		Log.i("DATA", "Se izvedem??");
		try {
			if (responce.getStatusCode() == RestCall.httpStatusOK) {
				String data = responce.getData();
				Log.i("DATA", data);
				// Log.i("DATA", data.getBytes().length + "");

				JSONArray array = new JSONArray(data);
				list = service.convertToArticleList(array);
				if (list.isEmpty()) {
					Toast.makeText(
							this.getActivity(),
							this.getActivity()
									.getResources()
									.getString(
											R.string.opozorilo_ni_podatkov_iskanje),
							Toast.LENGTH_SHORT).show();
				} else {
					displayNews();
				}
			} else {
				Toast.makeText(
						this.getActivity(),
						this.getActivity().getResources()
								.getString(R.string.opozorilo_ni_podatkov),
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		getLoaderManager().destroyLoader(RestCall.NALOZI_REST);
	}

	private void displayNews() {
		noviceAdapter = new ArticlesAdapter(getActivity()
				.getApplicationContext(), list);
		listViewResult.setAdapter(noviceAdapter);

	}

	@Override
	public void onLoaderReset(Loader<RestResponce> arg0) {

	}

	@Override
	public void onClick(View v) {
		String query = text.getText().toString();
		String noWhitespaces = query.trim();
		if (!noWhitespaces.isEmpty()) {
			hideKeybord();
			doSearch(query);
		} else {
			Toast.makeText(
					activity,
					activity.getResources().getString(
							R.string.opozorilo_ni_podatkov_iskanje),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void openArticleDetails(ArticleDAO article) {
		Intent intent = new Intent(this.getActivity(),
				ArticleDetailesActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("article", article);
		intent.putExtras(b);
		getActivity().startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ArticleDAO article = list.get(position);
		openArticleDetails(article);
	}

	public void hideKeybord() {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		} catch(Exception e){}
		
	}
}
