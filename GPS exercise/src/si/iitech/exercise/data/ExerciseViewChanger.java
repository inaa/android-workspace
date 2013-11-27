package si.iitech.exercise.data;

import java.util.List;

import si.iitech.exercise.R;
import si.iitech.exercise.fragment.ExerciseFragment;
import si.itech.swipe.ActivitySwipeDetector;
import si.itech.swipe.SwipeInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

public class ExerciseViewChanger implements SwipeInterface {

	private FrameLayout				frameLayoutDataArea;
	private List<ExerciseFragment>	exerciseFragmentList;
	private FragmentManager			fragmentManager;

	public ExerciseViewChanger(final FragmentActivity activity, final List<ExerciseFragment> exerciseFragmentList) {
		this.frameLayoutDataArea = (FrameLayout) activity.findViewById(R.id.frameLayoutDataArea);
		this.exerciseFragmentList = exerciseFragmentList;
		fragmentManager = activity.getSupportFragmentManager();
		setFragments();
		setSwipeListner();
	}

	private void setSwipeListner() {
		ActivitySwipeDetector activitySwipeDetector = new ActivitySwipeDetector(this);
		frameLayoutDataArea.setOnTouchListener(activitySwipeDetector);
	}

	private void setFragments() {
		changeFragment(exerciseFragmentList.get(0), R.id.frameLayoutDataArea);
		exerciseFragmentList.get(0).setOnView(true);
		frameLayoutDataArea.setTag(exerciseFragmentList.get(0));
	}

	private void changeFragment(final ExerciseFragment fragment, final int fragmenentLayout) {
		fragment.setOnView(true);
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(fragmenentLayout, fragment);
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragmentTransaction.commit();
	}

	private void displayNextFragment(int position, View view) {

		if (position == exerciseFragmentList.size()) {
			position = 0;
		}
		if (position == -1) {
			position = exerciseFragmentList.size() - 1;
		}
		ExerciseFragment fragment = exerciseFragmentList.get(position);
		changeFragment(fragment, view.getId());
		view.setTag(fragment);
	}

	@Override
	public void bottom2top(View v) {
		// NOT SUPPORTED
	}

	@Override
	public void left2right(View v) {

		ExerciseFragment fragment = (ExerciseFragment) v.getTag();
		int position = exerciseFragmentList.indexOf(fragment);
		fragment.setOnView(false);
		displayNextFragment(++position, v);
	}

	@Override
	public void right2left(View v) {

		ExerciseFragment fragment = (ExerciseFragment) v.getTag();
		int position = exerciseFragmentList.indexOf(fragment);
		fragment.setOnView(false);
		displayNextFragment(--position, v);
	}

	@Override
	public void top2bottom(View v) {
		// NOT SUPPORTED
	}

}
