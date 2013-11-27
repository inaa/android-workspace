package si.iitech.exercise.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.data.ExerciseDataGatherer;
import si.iitech.exercise.data.ExerciseViewChanger;
import si.iitech.exercise.data.ExerciseViewUpdater;
import si.iitech.exercise.fragment.DistanceFragment;
import si.iitech.exercise.fragment.ExerciseFragment;
import si.iitech.exercise.fragment.PaceFragment;
import si.iitech.exercise.fragment.SpeedFragment;
import si.iitech.exercise.fragment.TopSpeedFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

	private ExerciseData			exerciseData;

	private SpeedFragment			speedFragment;
	private TopSpeedFragment		topSpeedFragment;
	private DistanceFragment		distanceFragment;
	private PaceFragment			paceFragment;

	private List<ExerciseFragment>	exerciseFragmentList;

	private ExerciseViewChanger		exerciseViewChanger;
	private ExerciseDataGatherer	exerciseDataGatherer;
	private ExerciseViewUpdater		exerciseViewUpdater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initFragments();
		initExerciseData();
		initLocationListner();

		startExercise();
	}

	private void initExerciseData() {
		Locale locale = getResources().getConfiguration().locale;
		exerciseData = new ExerciseData(this, locale);

		// UPDATE EXERCISE DATA WITH GPS DATA
		exerciseDataGatherer = new ExerciseDataGatherer(this, exerciseData);

		// CHANGES VIEW
		exerciseViewChanger = new ExerciseViewChanger(this, exerciseFragmentList);

		// UPDATE FRAGMENTS EVERY SECOND
		exerciseViewUpdater = new ExerciseViewUpdater(exerciseData, exerciseFragmentList);

	}

	private void startExercise() {
		// TODO
	}

	private void initFragments() {
		Resources resources = getResources();
		speedFragment = ExerciseFragment.newInstanceSpeedFragment(resources);
		topSpeedFragment = ExerciseFragment.newInstanceTopSpeedFragment(resources);
		distanceFragment = ExerciseFragment.newInstanceDistanceFragment(resources);
		paceFragment = ExerciseFragment.newInstancePaceFragment(resources);

		// STORE FRAGMENTS FOR LATER USE
		exerciseFragmentList = new ArrayList<ExerciseFragment>();

		exerciseFragmentList.add(speedFragment);
		exerciseFragmentList.add(topSpeedFragment);
		exerciseFragmentList.add(distanceFragment);
		exerciseFragmentList.add(paceFragment);

	}

	private void initLocationListner() {

	}

	// public void updateData(ExerciseData exerciseData) {
	// for (ExerciseFragment exerciseFragment : exerciseFragmentList) {
	// if (exerciseFragment.isOnView()) {
	// exerciseFragment.displayExerciseData(exerciseData);
	// }
	// }
	// }

}
