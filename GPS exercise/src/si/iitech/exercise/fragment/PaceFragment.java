package si.iitech.exercise.fragment;

import si.iitech.exercise.data.ExerciseData;

public class PaceFragment extends ExerciseFragment {

	@Override
	public void displayExerciseData(ExerciseData exerciseData) {
		super.displayExerciseData(exerciseData);
		super.textViewValue.setText(exerciseData.getPaceFormat());

	}

	@Override
	public void displayLastExerciseData() {
		super.textViewValue.setText(super.lastExerciseData.getPaceFormat());
		
	}

	// @Override
	// public void displayLastData() {
	// if (null != this.exerciseData) {
	// textViewPaceValue.setText(this.exerciseData.getPaceFormat());
	// }
	//
	// }

}
