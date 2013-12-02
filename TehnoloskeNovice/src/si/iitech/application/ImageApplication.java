package si.iitech.application;

import si.iiteam.tehnoloskenovice.R;
import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		
		.resetViewBeforeLoading().delayBeforeLoading(0).cacheInMemory()
		.cacheOnDisc()
		.displayer(new FadeInBitmapDisplayer(500))
		.build();
		
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
	    	.threadPoolSize(3)
	    	.defaultDisplayImageOptions(options)
			.build();
		ImageLoader.getInstance().init(config);
		Log.i("Application", "onCreate");
	}

}
