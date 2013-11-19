package si.iitech.fragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public abstract class IITechFragment extends Fragment {

	protected abstract void initGraphicComponents();

	protected <T> void startNewActivity(Class<T> target) {
		Intent intent = new Intent(this.getActivity(), target);
		this.startActivity(intent);
	}

	protected void createToastMessage(String message) {
		Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
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

	protected void changeFragment(Fragment fragment, int fragmenentLayout) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(fragmenentLayout, fragment);
		fragmentTransaction.commit();
	}

	protected void serialize(Object o, String fileName) {
		try {
			FileOutputStream fos = getActivity().getApplicationContext()
					.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(o);
			os.close();
		} catch (IOException e) {
			log(e.toString());
		}
	}

	protected <T> T deserialize(Class<T> clazz, String fileName) {
		try {
			FileInputStream fis = getActivity().getApplicationContext()
					.openFileInput(fileName);
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
		Activity parentActivity = getActivity();
		SharedPreferences sharedPreferences = parentActivity
				.getSharedPreferences(getApplicationName(),
						Context.MODE_PRIVATE);
		SharedPreferences.Editor prefEditor = sharedPreferences.edit();
		prefEditor.putString(tag, value);
		prefEditor.commit();
	}

	protected String retriveFromSharedPreferences(String tag,
			String defaultValue) {
		Activity parentActivity = getActivity();
		SharedPreferences sharedPreferences = parentActivity
				.getSharedPreferences(getApplicationName(),
						Context.MODE_PRIVATE);
		return sharedPreferences.getString(tag, defaultValue);
	}

	protected String getApplicationName() {
		final PackageManager pm = getActivity().getApplicationContext()
				.getPackageManager();
		ApplicationInfo ai;
		try {
			ai = pm.getApplicationInfo(getActivity().getPackageName(), 0);
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
}
