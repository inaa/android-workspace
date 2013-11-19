package si.iitech.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IITechDate extends Date {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public IITechDate(long milis) {
		super(milis);
	}
	
	
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		return formatter.format(this);
	}
	
	public IITechDate toMillis(){
		
		return null;
	}
}
