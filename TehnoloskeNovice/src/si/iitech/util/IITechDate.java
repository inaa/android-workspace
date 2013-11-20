package si.iitech.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IITechDate extends Date {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public IITechDate(long milis) {
		super(milis);
	}
	
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		return formatter.format(this);
	}
	
	public IITechDate toMillis(){
		
		return null;
	}
}
