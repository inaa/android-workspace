package si.iitech.exercise.fragment;

import si.iitech.exercise.data.ExerciseData;

public class TopSpeedFragment extends ExerciseFragment {

	@Override
	public void displayExerciseData(ExerciseData exerciseData) {
		super.displayExerciseData(exerciseData);
		super.textViewValue.setText(exerciseData.getTopSpeedFormat());
	}

	@Override
	public void displayLastExerciseData() {
		super.textViewValue.setText(super.lastExerciseData.getTopSpeedFormat());

	}

}
