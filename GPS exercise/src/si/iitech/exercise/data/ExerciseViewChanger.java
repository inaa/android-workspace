package si.iitech.exercise.data;

import java.util.List;

import si.iitech.exercise.R;
import si.iitech.exercise.fragment.ExerciseFragment;
import si.itech.swipe.ActivitySwipeDetector;
import si.itech.swipe.SwipeInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class ExerciseViewChanger implements SwipeInterface {

	private FrameLayout frameLayoutDataArea;
	private List<ExerciseFragment> exerciseFragmentList;
	private FragmentActivity activity;

	public ExerciseViewChanger(final FragmentActivity activity,
			final FrameLayout frameLayoutDataArea,
			final List<ExerciseFragment> exerciseFragmentList) {
		this.frameLayoutDataArea = frameLayoutDataArea;
		this.exerciseFragmentList = exerciseFragmentList;
		this.activity = activity;
		setFragments();
		setSwipeListner();
	}

	private void setSwipeListner() {
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(
				this);
		frameLayoutDataArea.setOnTouchListener(activitySwipeDetector);
	}

	private void setFragments() {
		changeFragment(exerciseFragmentList.get(0), R.id.frameLayoutDataArea);
		exerciseFragmentList.get(0).setOnView(true);
		frameLayoutDataArea.setTag(exerciseFragmentList.get(0));
	}

	private void changeFragment(final Fragment fragment,
			final int fragmenentLayout) {
		FragmentManager fragmentManager = activity.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.replace(fragmenentLayout, fragment);
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.commit();
	}

	private void displayNextFragment(int position, View view) {
		Log.i("positon1", position + "");

		if (position == exerciseFragmentList.size()) {
			position = 0;
		}
		if (position == -1) {
			position = exerciseFragmentList.size() - 1;
		}
		Log.i("positon2", position + "");
		changeFragment(exerciseFragmentList.get(position), view.getId());
		view.setTag(exerciseFragmentList.get(position));
	}

	@Override
	public void bottom2top(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void left2right(View v) {

		ExerciseFragment fragment = (ExerciseFragment) v.getTag();
		int position = exerciseFragmentList.indexOf(fragment);
		fragment.setOnView(false);
		Log.i("positon3", position + "");
		position = position + 1;
		displayNextFragment(position, v);

	}

	@Override
	public void right2left(View v) {

		ExerciseFragment fragment = (ExerciseFragment) v.getTag();
		int position = exerciseFragmentList.indexOf(fragment);
		fragment.setOnView(false);
		position = position - 1;
		displayNextFragment(position, v);
	}

	@Override
	public void top2bottom(View v) {
		// NOT SUPPORTED
	}

	public void updateData(ExerciseData exerciseData) {
		for (ExerciseFragment exerciseFragment : exerciseFragmentList) {
			if (exerciseFragment.isOnView()) {
				exerciseFragment.updateData(exerciseData);
			}
		}
	}

}
