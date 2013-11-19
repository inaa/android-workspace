package si.iitech.activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public abstract class IITechActivity extends FragmentActivity {

	protected abstract void initGraphicComponents();

	protected <T> void startNewActivity(Class<T> target) {
		Intent intent = new Intent(this, target);
		this.startActivity(intent);
	}

	protected void createToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	protected void log(String TAG, String message) {
		Log.i(this.getClass().getSimpleName() + " " + TAG, message);
	}

	protected void log(String TAG, int value) {
		Log.i(this.getClass().getSimpleName() + " " + TAG, value + "");
	}

	protected void log(String message) {
		log(this.getClass().getSimpleName(), message);
	}

	protected void log(int value) {
		log(this.getClass().getSimpleName(), value);
	}

	/**
	 * �e ne deluje, poglej
	 * https://developers.facebook.com/docs/getting-started/
	 * facebook-sdk-for-android/3.0/
	 */

	protected void getKeyHasH() {
		String TAG = "KeyHash:";
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				log(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
			log(TAG, e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			log(TAG, e.getMessage());
		}
	}

	protected void changeFragment(Fragment fragment, int fragmenentLayout) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(fragmenentLayout, fragment);
		fragmentTransaction.commit();
	}

	protected void serialize(Object o, String fileName) {
		try {
			FileOutputStream fos = this.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(o);
			os.close();
		} catch (IOException e) {
			log(e.toString());
		}
	}

	protected <T> T deserialize(Class<T> clazz, String fileName) {
		try {
			FileInputStream fis = this.openFileInput(fileName);
			ObjectInputStream is = new ObjectInputStream(fis);
			T articleList = clazz.cast(is.readObject());
			is.close();
			return articleList;
		} catch (IOException e) {
			log(e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			log(e.toString());
			return null;
		}
	}

	protected void saveInSharedPreferences(String tag, String value) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getApplicationName(), MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = sharedPreferences.edit();
		prefEditor.putString(tag, value);
		prefEditor.commit();
	}

	protected String retriveFromSharedPreferences(String tag,
			String defaultValue) {
		SharedPreferences sharedPreferences = getSharedPreferences(
				getApplicationName(), MODE_PRIVATE);
		return sharedPreferences.getString(tag, defaultValue);
	}

	protected String getApplicationName() {
		final PackageManager pm = getApplicationContext().getPackageManager();
		ApplicationInfo ai;
		try {
			ai = pm.getApplicationInfo(this.getPackageName(), 0);
		} catch (final NameNotFoundException e) {
			ai = null;
		}
		final String applicationName = (String) (ai != null ? pm
				.getApplicationLabel(ai) : "(unknown)");
		return applicationName;
	}

	protected void showWebSite(String url) {
		Intent webIntent = new Intent(Intent.ACTION_VIEW);
		webIntent.setData(Uri.parse(url));
		startActivity(webIntent);
	}

	protected void createYesNoAlertDialog(String message,
			String positiveButtonMessage, String negativeButtonMessage,
			OnClickListener onClickListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
				.setPositiveButton(positiveButtonMessage, onClickListener)
				.setNegativeButton(negativeButtonMessage, onClickListener).show();
	}
	
	protected boolean checkIfEditTextIsEmpty(EditText editText) {
		if(0 == editText.getText().length()) {
			return true;
		}
		return false;
	}
}
