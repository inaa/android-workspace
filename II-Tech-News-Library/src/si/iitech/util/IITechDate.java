package si.iitech.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IITechDate extends Date {
	
	private static final long serialVersionUID = 1L;

	public IITechDate(long milis) {
		super(milis);
	}
	
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
		return formatter.format(this);
	}
}
