package si.iitech.exercise.data;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class ExerciseDataGatherer implements LocationListener {

	private ExerciseData	exerciseData;
	private Context			context;
	private LocationManager	locationManager;

	public ExerciseDataGatherer(final Context context, ExerciseData exerciseData) {
		this.context = context;
		this.exerciseData = exerciseData;
		locationManager = (LocationManager) context.getSystemService(Activity.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	@Override
	public void onLocationChanged(Location location) {
		exerciseData.formatData(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(context, provider, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(context, provider, Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStatusChanged(String status, int numberOfSatelites, Bundle arg2) {
		Toast.makeText(context, status + " " + numberOfSatelites, Toast.LENGTH_LONG).show();

	}

}
