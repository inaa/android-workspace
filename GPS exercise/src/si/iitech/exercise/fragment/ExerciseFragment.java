package si.iitech.exercise.fragment;

import si.iitech.exercise.data.ExerciseData;
import android.support.v4.app.Fragment;

public abstract class ExerciseFragment extends Fragment {
	
	private boolean onView;
	
	protected ExerciseData exerciseData;

	public abstract void updateData(final ExerciseData data);
	
	public abstract void displayLastData();
	
	public void setOnView(boolean onView) {
		this.onView = onView;
	}
	
	public boolean isOnView() {
		return onView;
	}

}
