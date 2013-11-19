package si.iitech.util;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.text.format.Time;

public class IITechUtil {

	public static String generateRandomUIID() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}

	public static long getCurrentTime() {
		Time t = new Time();
		t.setToNow();
		return t.toMillis(true);
	}

	public static String formatTime(long time) {
		Time t = new Time();
		t.set(time);
		return t.format("%d.%m.%Y %H:%M:%S");
	}
	
	public static <T> void startNewActivity(Class<T> target, final Activity activity) {
		Intent intent = new Intent(activity, target);
		activity.startActivity(intent);
	}

	/**
	 * public static void log(Class<?> clazz, String TAG, String message) {
	 * Log.i(clazz.getName() + " " + TAG, message); }
	 * 
	 * public static void log(Class<?> clazz, String TAG, int value) {
	 * Log.i(clazz.getName() + " " + TAG, value + ""); }
	 * 
	 * public static void log(Class<?> clazz, String message) { log(clazz,
	 * message); }
	 * 
	 * public static void log(Class<?> clazz, int value) { log(clazz, value); }
	 */
}
