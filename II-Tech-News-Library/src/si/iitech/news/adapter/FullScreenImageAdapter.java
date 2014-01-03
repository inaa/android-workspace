package si.iitech.news.adapter;

import java.util.ArrayList;

import si.iitech.news.R;
import si.iitech.util.TouchImageView;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class FullScreenImageAdapter extends PagerAdapter {

	private Context _context;
	private ArrayList<String> _imagePaths;
	private ArrayList<String> _imageTitles;
	private LayoutInflater inflater;
	private DisplayImageOptions options;
	private ImageLoader imageLoader;

	// constructor
	public FullScreenImageAdapter(Context activity, ArrayList<String> imagePaths, ArrayList<String> imageTitles) {

		this._context = activity;
		this._imagePaths = imagePaths;
		this._imageTitles = imageTitles;
		initImageLoader();
	}

	private void initImageLoader() {
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_big).showImageForEmptyUri(R.drawable.icon_big)
				.showImageOnFail(R.drawable.icon_big).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).resetViewBeforeLoading().delayBeforeLoading(0)
				.cacheInMemory().cacheOnDisc().displayer(new FadeInBitmapDisplayer(500)).build();
		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		return this._imagePaths.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TouchImageView imgDisplay;
		TextView imageTitle;

		inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

		imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
		// imgDisplay.setScaleType(ScaleType.CENTER);
		imageTitle = (TextView) viewLayout.findViewById(R.id.image_title);
		imageLoader.displayImage(_imagePaths.get(position), imgDisplay, options);

		if (!_imageTitles.get(position).equalsIgnoreCase(null)) {
			imageTitle.setVisibility(View.VISIBLE);
			imageTitle.setText(_imageTitles.get(position));
		}

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((LinearLayout) object);

	}

}
