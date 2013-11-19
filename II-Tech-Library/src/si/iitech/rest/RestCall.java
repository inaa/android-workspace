package si.iitech.rest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class RestCall extends AsyncTaskLoader<RestResponce> {

	private static final String TAG = RestCall.class.getName();
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

	public static RestCall createRestService(Context context,
			RestEnum typeOfService, Uri url) {
		return new RestCall(context, typeOfService, url, null);
	}

	public static RestCall createRestServiceWithJson(Context context,
			RestEnum typeOfService, Uri url, String json) {
		return new RestCall(context, typeOfService, url, json);
	}

	public RestCall(Context context, RestEnum typeOfService, Uri url,
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
				Log.i("POST", "POST");
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
				// HttpClient client = new DefaultHttpClient();
				// request.addHeader("Content-Type", contentType);
				// request.addHeader("Content-Encoding", "gzip");
				//
				// HttpResponse response = client.execute(request);
				// HttpEntity responseEntity = response.getEntity();
				// StatusLine responseStatus = response.getStatusLine();
				// int statusCode = responseStatus != null ? responseStatus
				// .getStatusCode() : 0;
				// RestResponce restResponse = new RestResponce(
				// responseEntity != null ? EntityUtils.toString(responseEntity)
				// : null, statusCode);
				HttpClient client = new DefaultHttpClient();
				request.addHeader("Content-Type", contentType);
				request.addHeader("Accept-Encoding", "gzip");

				HttpResponse response = client.execute(request);
				StatusLine responseStatus = response.getStatusLine();
				int statusCode = responseStatus != null ? responseStatus
						.getStatusCode() : 0;

				InputStream is = response.getEntity().getContent();
				// GZIPInputStream zis = null;

				Header contentEncoding = response
						.getFirstHeader("Content-Encoding");
				if (contentEncoding != null
						&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					Log.i("REST CALL", "gzip!");
					is = new GZIPInputStream(new BufferedInputStream(is));
				}
				String stringResponce = readInputStreamAsString(is);
				//Log.i("RestCall", stringResponce);
				RestResponce restResponse = new RestResponce(stringResponce,
						statusCode);
				return restResponse;
			}
		} catch (Exception e) {
			return new RestResponce();
		}
		return new RestResponce();
	}

	public String readInputStreamAsString(InputStream is) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				Log.i("RETURN LINE", line);

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public byte[] decompress(byte[] compressed) throws IOException {
		GZIPInputStream gzipInputStream;
		if (compressed.length > 4) {
			gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(
					compressed, 4, compressed.length - 4));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int value = 0; value != -1;) {
				value = gzipInputStream.read();
				if (value != -1) {
					baos.write(value);
				}
			}
			gzipInputStream.close();
			baos.close();

			return baos.toByteArray();
		} else {
			return null;
		}
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
