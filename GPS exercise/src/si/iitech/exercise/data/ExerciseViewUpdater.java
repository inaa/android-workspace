package si.iitech.exercise.data;

import java.util.List;

import si.iitech.exercise.fragment.ExerciseFragment;
import android.os.Handler;

public class ExerciseViewUpdater implements Runnable {

	private ExerciseData			exerciseData;
	private List<ExerciseFragment>	exerciseFragmentList;
	private Handler					handler;

	public ExerciseViewUpdater(ExerciseData exerciseData, List<ExerciseFragment> exerciseFragmentList) {
		this.exerciseData = exerciseData;
		this.exerciseFragmentList = exerciseFragmentList;
		updateExerciseFragments();
	}

	private void updateExerciseFragments() {
		handler = new Handler();
		handler.postDelayed(this, 1000);

	}

	@Override
	public void run() {
		for (ExerciseFragment exerciseFragment : exerciseFragmentList) {
			if (exerciseFragment.isOnView()) {
				exerciseFragment.displayExerciseData(exerciseData);
			}
		}
		handler.postDelayed(this, 1000);

	}

}
