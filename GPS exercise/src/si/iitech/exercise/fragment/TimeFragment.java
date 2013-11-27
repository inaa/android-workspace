package si.iitech.exercise.fragment;

import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

public class TimeFragment extends ExerciseFragment implements OnChronometerTickListener {

	private Chronometer	chronometer;

	public TimeFragment() {
		chronometer = new Chronometer(getActivity());
	}

	@Override
	public void displayLastExerciseData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChronometerTick(Chronometer chronometer) {
		// TODO Auto-generated method stub

	}

}
