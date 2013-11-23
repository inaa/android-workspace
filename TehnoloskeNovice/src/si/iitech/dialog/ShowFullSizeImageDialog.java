package si.iitech.dialog;

import java.util.ArrayList;
import java.util.List;

import si.iiteam.tehnoloskenovice.R;
import si.iitech.news.adapter.FullScreenImageAdapter;
import si.iitech.news.dao.MediaElement;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.ViewPager;


public class ShowFullSizeImageDialog extends Dialog {

	private FullScreenImageAdapter adapter;
	private ViewPager viewPager;
	
	public ShowFullSizeImageDialog(Context context, List<MediaElement> images) {
		super(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		setContentView(R.layout.activity_fullscreen_view);
	
		
		ArrayList<String> imagePaths = new ArrayList<String>();
    	for (MediaElement mediaElement : images) {
			imagePaths.add(mediaElement.getHref());
		}
    	
    	ArrayList<String> imageTitles = new ArrayList<String>();
    	for (MediaElement mediaElement : images) {
			imageTitles.add(mediaElement.getTitle());
		}

		viewPager = (ViewPager) findViewById(R.id.pager);


		adapter = new FullScreenImageAdapter(context,
				imagePaths, imageTitles);

		viewPager.setAdapter(adapter);
	}
	
	public static void createDialog(final Context context,
			final List<MediaElement> mediaElementImages) {
		new ShowFullSizeImageDialog(context,
				mediaElementImages).show();
	}

}
