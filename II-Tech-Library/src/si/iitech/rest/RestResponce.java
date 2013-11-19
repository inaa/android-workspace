package si.iitech.rest;

import android.util.Log;

public class RestResponce {

	private static final String TAG = RestResponce.class.getName();

	private int statusCode;
	private String data;

	public RestResponce() {

	}

	public RestResponce(String data, int statusCode) {
		this.statusCode = statusCode;
		this.data = data;
		Log.i(TAG, "statuCode: " + statusCode + " data: " + data);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getData() {
		return data;
	}

}
