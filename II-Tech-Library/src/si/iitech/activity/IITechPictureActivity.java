package si.iitech.activity;

import si.iitech.util.IITechUtil;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public abstract class IITechPictureActivity extends IITechActivity {

	protected final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0x1;
	private final String CAPTURE_IMAGE_DESCRIPTION = "Image was captured by 3rd party app";

	protected Uri useCamera() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE,
				IITechUtil.generateRandomUIID());
		values.put(MediaStore.Images.Media.DESCRIPTION,
				CAPTURE_IMAGE_DESCRIPTION);
		Uri imageUri = this.getContentResolver().insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		this.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		return imageUri;
	}

}
