package si.iitech.exercise.data;

import java.text.NumberFormat;
import java.util.Locale;

import android.content.Context;
import android.location.Location;

public class ExerciseData {

	private Location previusLocation;

	private long startTime;
	private long endTime;

	private float distance = 0.0f;;
	private double speed = 0.0;
	private double avgSpeed = 0.0;
	private double topSpeed = 0.0;

	private long pace = 0l;

	private int calories;

	private Locale locale;

	private final double MIN_SPEED = 0.5;
	private final double NO_VALUE = 0.0;

	public ExerciseData(final Context context) {
		startTime = System.currentTimeMillis();
		locale = context.getResources().getConfiguration().locale;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getPace() {
		return 0;
	}

	public double getAvgSpeed() {
		return avgSpeed;
	}

	public double getTopSpeed() {
		return topSpeed;
	}

	public float getDistance() {
		return distance;
	}

	public int getCalories() {
		return calories;
	}

	public void formatData(Location location) {
		double tempSpeed = location.getSpeed();

		if (tempSpeed > MIN_SPEED) {
			// SPEED
			this.speed = tempSpeed;

			// TOP SPEED
			if (tempSpeed > this.topSpeed) {
				this.topSpeed = tempSpeed;
			}
			// PACE
			pace = (long) (((1) / (speed)) * 3600000);

		} else {
			this.speed = NO_VALUE;
		}

		if (null != this.previusLocation && tempSpeed > MIN_SPEED) {
			this.distance = this.distance
					+ location.distanceTo(previusLocation);

		}
		this.previusLocation = location;
	}

	private String round(final double value, int numberODigits) {
		NumberFormat formatter = NumberFormat.getInstance(locale);
		formatter.setMaximumFractionDigits(numberODigits);
		formatter.setMinimumFractionDigits(numberODigits);
		return formatter.format(value);
	}

	private String formatTime(long millis) {
		//MANJKA 0 pri minutah èe je potrebna, boljši bi bil drugi naèin
		int seconds = (int) (millis / 1000) % 60 ;
		int minutes = (int) ((millis / (1000*60)) % 60);
		return minutes + ":" + seconds;
	}

	public String getSpeedFormat() {
		return round(speed, 1);
	}

	public String getTopSpeedFormat() {
		return round(topSpeed, 1);
	}

	public String getDistanceFormat() {
		return round(distance, 1);
	}

	public String getPaceFormat() {
		return formatTime(pace);
	}

}
