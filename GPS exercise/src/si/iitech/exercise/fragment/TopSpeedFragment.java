package si.iitech.exercise.fragment;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.util.Fonts;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopSpeedFragment extends ExerciseFragment {

	private TextView textViewTopSpeedValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_top_speed, container,
				false);
		textViewTopSpeedValue = (TextView) view
				.findViewById(R.id.textView_top_speed_value);
		textViewTopSpeedValue.setTypeface(Fonts.getInstance(getActivity())
				.getTypeFaceDigitalFontBold());
		displayLastData();
		return view;
	}

	@Override
	public void updateData(ExerciseData data) {
		textViewTopSpeedValue.setText(data.getTopSpeedFormat());
		this.exerciseData = data;
	}

	@Override
	public void displayLastData() {
		if (null != this.exerciseData) {
			textViewTopSpeedValue
					.setText(this.exerciseData.getTopSpeedFormat());
			Log.i("LAST DATA", "NOT NULL");
		} else {
			Log.i("LAST DATA", "NULL");
		}

	}

}
