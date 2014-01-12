package si.iitech.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class Serialize {

	public static final String	prefixList	= ".list";
	public static final String	prefixTime	= ".time";

	public static void serialize(Activity activity, Object o, String file, String prefix) {
		try {
			FileOutputStream fos = activity.getApplicationContext().openFileOutput(file + prefix, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(o);
			os.close();
		} catch (IOException e) {
			Log.i("Serialize, serialize, IOException, ", e.toString());
		}
	}

	public static <T> T deserialize(Activity activity, Class<T> clazz, String file, String prefix) {
		try {
			FileInputStream fis = activity.getApplicationContext().openFileInput(file + prefix);
			ObjectInputStream is = new ObjectInputStream(fis);
			T result = clazz.cast(is.readObject());
			is.close();
			return result;
		} catch (IOException e) {
			Log.i("Serialize, deserialize, IOException, ", e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			Log.i("Serialize, deserialize, ClassNotFoundException, ", e.toString());
			return null;
		}
	}

}
