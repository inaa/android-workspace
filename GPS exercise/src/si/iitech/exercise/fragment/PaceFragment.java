package si.iitech.exercise.fragment;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.util.Fonts;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PaceFragment extends ExerciseFragment {

	private TextView textViewPaceValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pace, container, false);
		textViewPaceValue = (TextView) view
				.findViewById(R.id.textView_pace_value);
		textViewPaceValue.setTypeface(Fonts.getInstance(getActivity())
				.getTypeFaceDigitalFontBold());
		displayLastData();
		return view;
	}

	@Override
	public void updateData(ExerciseData data) {
		textViewPaceValue.setText(data.getPaceFormat());
		this.exerciseData = data;
	}

	@Override
	public void displayLastData() {
		if (null != this.exerciseData) {
			textViewPaceValue.setText(this.exerciseData.getPaceFormat());
		}

	}

}
