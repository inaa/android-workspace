package si.iitech.exercise.util;

import android.content.Context;
import android.graphics.Typeface;

public class Fonts {

	private static Fonts instance;

	private Typeface typefaceDigitalFontBold;
	private Typeface typefaceDigitalFont;

	public static Fonts getInstance(final Context context) {
		if (instance == null) {
			instance = new Fonts(context);
		}
		return instance;
	}

	public Fonts(final Context context) {
		typefaceDigitalFont = Typeface.createFromAsset(context.getAssets(),
				"fonts/DS-DIGI.TTF");
		typefaceDigitalFontBold = Typeface.createFromAsset(context.getAssets(),
				"fonts/DS-DIGIB.TTF");
	}

	public Typeface getTypeFaceDigitalFont() {
		return typefaceDigitalFont;
	}

	public Typeface getTypeFaceDigitalFontBold() {
		return typefaceDigitalFontBold;
	}

}
