package si.iitech.exercise.fragment;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.util.Fonts;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ExerciseFragment extends Fragment {

	private boolean				onView;

	protected TextView			textViewValue;
	protected TextView			textViewValueTitle;
	protected TextView			textViewValueUnits;
	protected RelativeLayout	background;

	public static final String	KEY_TEXT_VIEW_VALUE_TITLE	= "1332";
	public static final String	KEY_TEXT_VIEW_VALUE_UNITS	= "1333";
	public static final String	KEY_COLOR					= "1334";

	private String				title;
	private String				units;
	private int					color;

	protected ExerciseData		lastExerciseData;

	public void displayExerciseData(final ExerciseData exerciseData) {
		this.lastExerciseData = exerciseData;
	}

	public abstract void displayLastExerciseData();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_values, container, false);
		// INIT VIEWS
		textViewValue = (TextView) view.findViewById(R.id.textView_value);
		textViewValueTitle = (TextView) view.findViewById(R.id.textView_value_title);
		textViewValueUnits = (TextView) view.findViewById(R.id.textView_value_unit);

		// SET TYPEFACE
		Typeface typeface = Fonts.getInstance(getActivity()).getTypefaceDigitalFontBold();
		textViewValue.setTypeface(typeface);
		textViewValueTitle.setTypeface(typeface);
		textViewValueUnits.setTypeface(typeface);

		// SET BACKGROUND
		background = (RelativeLayout) view.findViewById(R.id.background_value);

		// SET VALUES
		textViewValueTitle.setText(title);
		textViewValueUnits.setText(units);
		background.setBackgroundColor(color);

		if (lastExerciseData != null) {
			displayLastExerciseData();
		}

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			title = args.getString(KEY_TEXT_VIEW_VALUE_TITLE);
			units = args.getString(KEY_TEXT_VIEW_VALUE_UNITS);
			color = args.getInt(KEY_COLOR);
		}
	}

	public static DistanceFragment newInstanceDistanceFragment(final Resources resources) {
		Bundle b = new Bundle();
		b.putString(KEY_TEXT_VIEW_VALUE_TITLE, resources.getString(R.string.distance_title));
		b.putString(KEY_TEXT_VIEW_VALUE_UNITS, resources.getString(R.string.distance_metric_unit));
		b.putInt(KEY_COLOR, resources.getColor(R.color.distance));

		DistanceFragment distanceFragment = new DistanceFragment();
		distanceFragment.setArguments(b);

		return distanceFragment;
	}

	public static PaceFragment newInstancePaceFragment(final Resources resources) {
		Bundle b = new Bundle();
		b.putString(KEY_TEXT_VIEW_VALUE_TITLE, resources.getString(R.string.pace_title));
		b.putString(KEY_TEXT_VIEW_VALUE_UNITS, resources.getString(R.string.pace_metric_unit));
		b.putInt(KEY_COLOR, resources.getColor(R.color.pace));

		PaceFragment paceFragment = new PaceFragment();
		paceFragment.setArguments(b);

		return paceFragment;
	}

	public static SpeedFragment newInstanceSpeedFragment(final Resources resources) {
		Bundle b = new Bundle();
		b.putString(KEY_TEXT_VIEW_VALUE_TITLE, resources.getString(R.string.speed_title));
		b.putString(KEY_TEXT_VIEW_VALUE_UNITS, resources.getString(R.string.speed_metric_unit));
		b.putInt(KEY_COLOR, resources.getColor(R.color.speed));

		SpeedFragment speedFragment = new SpeedFragment();
		speedFragment.setArguments(b);

		return speedFragment;
	}

	public static TopSpeedFragment newInstanceTopSpeedFragment(final Resources resources) {
		Bundle b = new Bundle();
		b.putString(KEY_TEXT_VIEW_VALUE_TITLE, resources.getString(R.string.top_speed_title));
		b.putString(KEY_TEXT_VIEW_VALUE_UNITS, resources.getString(R.string.speed_metric_unit));
		b.putInt(KEY_COLOR, resources.getColor(R.color.top_speed));

		TopSpeedFragment topSpeedFragment = new TopSpeedFragment();
		topSpeedFragment.setArguments(b);

		return topSpeedFragment;
	}

	// GETTER AND SETTER

	public void setOnView(boolean onView) {
		this.onView = onView;
	}

	public boolean isOnView() {
		return onView;
	}

}
