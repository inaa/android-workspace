package si.iitech.exercise.fragment;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.util.Fonts;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SpeedFragment extends ExerciseFragment {

	private TextView textViewSpeedValue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_speed, container, false);
		textViewSpeedValue = (TextView) view
				.findViewById(R.id.textView_speed_value);
		textViewSpeedValue.setTypeface(Fonts.getInstance(getActivity())
				.getTypeFaceDigitalFontBold());
		displayLastData();
		return view;
	}

	@Override
	public void updateData(ExerciseData data) {
		textViewSpeedValue.setText(data.getSpeedFormat());
		this.exerciseData = data;
	}

	@Override
	public void displayLastData() {
		if(null != this.exerciseData) {
			textViewSpeedValue.setText(this.exerciseData.getSpeedFormat());
		}
		
	}

}
