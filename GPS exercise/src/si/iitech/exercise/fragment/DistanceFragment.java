package si.iitech.exercise.fragment;

import si.iitech.exercise.data.ExerciseData;

public class DistanceFragment extends ExerciseFragment {
	
	@Override
	public void displayExerciseData(ExerciseData exerciseData) {
		super.displayExerciseData(exerciseData);
		super.textViewValue.setText(exerciseData.getDistanceFormat());
	}

	@Override
	public void displayLastExerciseData() {
		super.textViewValue.setText(super.lastExerciseData.getDistanceFormat());
	}
}
