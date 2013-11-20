package si.iitech.exercise.fragment;

import si.iitech.exercise.R;
import si.iitech.exercise.data.ExerciseData;
import si.iitech.exercise.util.Fonts;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DistanceFragment extends ExerciseFragment {

	private TextView textViewDistanceValue;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_distance, container,
				false);
		textViewDistanceValue = (TextView) view
				.findViewById(R.id.textView_distance_value);
		textViewDistanceValue.setTypeface(Fonts.getInstance(getActivity())
				.getTypeFaceDigitalFontBold());
		displayLastData();
		return view;
	}

	@Override
	public void updateData(ExerciseData data) {
		textViewDistanceValue.setText(data.getDistanceFormat());
		this.exerciseData = data;
	}

	@Override
	public void displayLastData() {
		if(null != this.exerciseData) {
			textViewDistanceValue.setText(this.exerciseData.getDistanceFormat());
		}
		
	}

}
