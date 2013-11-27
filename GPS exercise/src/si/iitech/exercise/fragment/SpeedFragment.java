package si.iitech.exercise.fragment;

import si.iitech.exercise.data.ExerciseData;

public class SpeedFragment extends ExerciseFragment {

	@Override
	public void displayExerciseData(ExerciseData exerciseData) {
		super.displayExerciseData(exerciseData);
		super.textViewValue.setText(exerciseData.getSpeedFormat());

	}

	@Override
	public void displayLastExerciseData() {
		super.textViewValue.setText(super.lastExerciseData.getSpeedFormat());
	}

}
