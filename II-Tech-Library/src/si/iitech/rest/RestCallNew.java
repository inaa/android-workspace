package si.iitech.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

public class RestCallNew extends AsyncTaskLoader<RestResponce> {

	private static final String TAG = RestCallNew.class.getName();
	private RestEnum typeOfService;
	private Uri url = null;
	private String json = null;
	private RestResponce responce;

	public static final int NALOZI_REST = 0x1;
	public static final String URI_REST = "URI_REST";
	public static final String JSON_REST = "JSON_REST";
	public static final int httpStatusOK = 200;
	public static final int httpStatusCreated = 201;
	public static final int httpStatusNotModified = 304;
	public static final String isSignedIn = "1";

	private long lastLoad;

	private final String contentType = "application/json";

	private static final long STALE_DELTA = 0;

	public static RestCallNew createRestService(Context context,
			RestEnum typeOfService, Uri url) {
		return new RestCallNew(context, typeOfService, url, null);
	}

	public static RestCallNew createRestServiceWithJson(Context context,
			RestEnum typeOfService, Uri url, String json) {
		return new RestCallNew(context, typeOfService, url, json);
	}

	public RestCallNew(Context context, RestEnum typeOfService, Uri url,
			String json) {
		super(context);
		this.typeOfService = typeOfService;
		this.url = url;
		this.json = json;
	}

	@Override
	public RestResponce loadInBackground() {
		Log.i(TAG, "loadInBackground()");
		try {
			if (url == null) {
				Log.e(TAG, "URL is empty");
				return new RestResponce();
			}
			HttpRequestBase request = null;
			switch (typeOfService) {
			case GET: {
				request = new HttpGet();
				request.setURI(new URI(url.toString()));
			}
				break;
			case POST: {
				request = new HttpPost();
				request.setURI(new URI(url.toString()));
				HttpPost postRequest = (HttpPost) request;
				StringEntity entity = new StringEntity(json, "UTF-8");
				entity.setContentType(contentType);
				postRequest.setEntity(entity);
			}
				break;

			}
			if (request != null) {
				HttpClient client = new DefaultHttpClient();
				request.addHeader("Content-Type", contentType);
				request.addHeader("Content-Encoding", "gzip");

				HttpResponse response = client.execute(request);
				StatusLine responseStatus = response.getStatusLine();
				int statusCode = responseStatus != null ? responseStatus
						.getStatusCode() : 0;

				InputStream instream = response.getEntity().getContent();
			
				Header contentEncoding = response
						.getFirstHeader("Content-Encoding");
				if (contentEncoding != null
						&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					instream = new GZIPInputStream(instream);
				}
				String stringResponce = readInputStreamAsString(instream);
				RestResponce restResponse = new RestResponce(stringResponce, statusCode);
				// RestResponce restResponse = new RestResponce(
				// responseEntity != null ? EntityUtils.toString(responseEntity)
				// : null, statusCode);
				return restResponse;
			}
		} catch (Exception e) {
			return new RestResponce();
		}
		return new RestResponce();
	}
	
	public String readInputStreamAsString(InputStream in) 
		    throws IOException {

		    BufferedInputStream bis = new BufferedInputStream(in);
		    ByteArrayOutputStream buf = new ByteArrayOutputStream();
		    int result = bis.read();
		    while(result != -1) {
		      byte b = (byte)result;
		      buf.write(b);
		      result = bis.read();
		    }        
		    return buf.toString();
		}

	@Override
	public void deliverResult(RestResponce data) {
		Log.i(TAG, "deliverResult()");
		responce = data;
		super.deliverResult(data);
	}

	@Override
	protected void onStartLoading() {
		Log.i(TAG, "onStartLoading()");
		if (responce != null) {
			super.deliverResult(responce);
		}
		if (responce == null
				|| System.currentTimeMillis() - lastLoad >= STALE_DELTA) {
			forceLoad();
		}

	}

	@Override
	protected void onStopLoading() {
		cancelLoad();
	}

	@Override
	protected void onReset() {
		super.onReset();
		onStopLoading();
		responce = null;
		lastLoad = 0;
	}

}
