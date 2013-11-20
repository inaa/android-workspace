package si.iitech.exercise.activity;

import java.util.ArrayList;
import java.util.List;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.data.ExerciseViewChanger;
import si.iitech.exercise.fragment.DistanceFragment;
import si.iitech.exercise.fragment.ExerciseFragment;
import si.iitech.exercise.fragment.PaceFragment;
import si.iitech.exercise.fragment.SpeedFragment;
import si.iitech.exercise.fragment.TopSpeedFragment;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LocationListener {

	private LocationManager locationManager;
	private ExerciseData exerciseData;

	private SpeedFragment speedFragment;
	private TopSpeedFragment topSpeedFragment;
	private DistanceFragment distanceFragment;
	private PaceFragment paceFragment;

	private List<ExerciseFragment> exerciseFragmentList;

	private FrameLayout frameLayoutDataArea;

	private ExerciseViewChanger exerciseViewChanger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initFragments();
		initView();
		initLocationListner();
		startExercise();

	}

	private void startExercise() {
		exerciseData = new ExerciseData(this);
	}

	private void initFragments() {
		speedFragment = new SpeedFragment();
		topSpeedFragment = new TopSpeedFragment();
		distanceFragment = new DistanceFragment();
		paceFragment = new PaceFragment();

		exerciseFragmentList = new ArrayList<ExerciseFragment>();

		exerciseFragmentList.add(speedFragment);
		exerciseFragmentList.add(topSpeedFragment);
		exerciseFragmentList.add(distanceFragment);
		exerciseFragmentList.add(paceFragment);

	}

	private void initLocationListner() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);

	}

	private void initView() {
		frameLayoutDataArea = (FrameLayout) findViewById(R.id.frameLayoutDataArea);
		exerciseViewChanger = new ExerciseViewChanger(this,
				frameLayoutDataArea, exerciseFragmentList);
	}

	@Override
	public void onLocationChanged(Location location) {
		exerciseData.formatData(location);
		exerciseViewChanger.updateData(exerciseData);
		// speedFragment.updateData(exerciseData);
		// distanceFragment.updateData(exerciseData);
		// topSpeedFragment.updateData(exerciseData);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, provider, Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, provider, Toast.LENGTH_LONG).show();
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String status, int numberOfSatelites,
			Bundle arg2) {
		Toast.makeText(this, status + " " + numberOfSatelites,
				Toast.LENGTH_LONG).show();

	}

}
