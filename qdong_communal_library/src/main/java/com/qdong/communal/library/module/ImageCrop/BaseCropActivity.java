package com.qdong.communal.library.module.ImageCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;


import com.qdong.communal.library.R;
import com.qdong.communal.library.module.base.BaseActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * The activity can crop specific region of interest from an image.
 */
public abstract class BaseCropActivity extends BaseActivity {

	// These options specifiy the output image size and whether we should
	// scale the output to fit it (or just crop it).
	protected abstract void onChooseCompleted(String strPath) ;

	protected abstract View getTitleView();

	// private static final String TAG = "CropImage";
	private static final boolean RECYCLE_INPUT = true;
	/** show Dialog for saving progress message id */
	protected static final int SHOW_PROGRESSBAR = 0xe101;
	/** close show Dialog for saving progress message id */
	protected static final int DISS_PROGRESSBAR = 0xe102;
	/** temp save file path */
	private String strPath = "";
	/** this activity context */
	Context m_context;
	private int mAspectX, mAspectY;

	private final Handler mHandler = new Handler();
	private int mOutputX, mOutputY;
	private boolean mScale;
	private boolean mScaleUp = true;

	private boolean mCircleCrop = false;

	boolean mSaving; // Whether the "save" button is already clicked.
	private CropImageView mImageView;
	/** progress dialog for save progress */
	private ProgressDialog savingProgressDialog;
	private Bitmap mBitmap;


	HighlightView mCrop;

	@SuppressLint("HandlerLeak")
	private final Handler myCropHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESSBAR:
				savingProgressDialog = new ProgressDialog(m_context);
				savingProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//
				savingProgressDialog.setMessage("saving .....");
				savingProgressDialog.setIndeterminate(false);//
				break;
			case DISS_PROGRESSBAR:
				savingProgressDialog.dismiss();

				/**抽象出来,子类实现**/
				onChooseCompleted(strPath);

				/*Bundle extras = new Bundle();
				extras.putString("data-src", strPath);
				setData(RESULT_OK, (new Intent()).setAction("inline-data").putExtras(extras));
				overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
				finish();*/
				break;
			default:
				break;
			}

		};
	};
	private LinearLayout mLlTitleContainer;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_cropimage);

		mLlTitleContainer= (LinearLayout) findViewById(R.id.rl_photo_top);
		if(getTitleView()!=null){
			mLlTitleContainer.addView(getTitleView());
		}

		mImageView = (CropImageView) findViewById(R.id.image);
		mImageView.mContext = this;
		m_context = this;

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();

		if (extras != null) {
			mBitmap = (Bitmap) extras.getParcelable("data");
			mAspectX = extras.getInt("aspectX");
			mAspectY = extras.getInt("aspectY");
			mOutputX = extras.getInt("outputX");
			mOutputY = extras.getInt("outputY");
			mScale = extras.getBoolean("scale", true);
			mScaleUp = extras.getBoolean("scaleUpIfNeeded", true);
		}

		if (mBitmap == null) {
			InputStream is = null;
			try {
				Uri target = intent.getData();
				ContentResolver cr = getContentResolver();
				is = cr.openInputStream(target);
				mBitmap = BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException ignored) {
					}
				}
			}
		}

		if (mBitmap == null) {
			finish();
			return;
		}

		// Make UI fullscreen.
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);



		startFaceDetection();
	}



	private void startFaceDetection() {
		if (isFinishing()) {
			return;
		}

		mImageView.setImageBitmapResetBase(mBitmap, true);

		startBackgroundJob(this, null, "xixi", new Runnable() {
			public void run() {
				final CountDownLatch latch = new CountDownLatch(1);
				final Bitmap b = mBitmap;
				mHandler.post(new Runnable() {
					public void run() {
						if (b != mBitmap && b != null) {
							mImageView.setImageBitmapResetBase(b, true);
							mBitmap.recycle();
							mBitmap = b;
						}
						if (mImageView.getScale() == 1F) {
							mImageView.center(true, true);
						}
						latch.countDown();
					}
				});
				try {
					latch.await();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				mRunFaceDetection.run();
			}
		}, mHandler);
	}

	private static class BackgroundJob extends BaseActivity.LifeCycleAdapter implements Runnable {

		private final BaseActivity mActivity;
		private final ProgressDialog mDialog;
		private final Runnable mJob;
		private final Handler mHandler;
		private final Runnable mCleanupRunner = new Runnable() {
			public void run() {
				mActivity.removeLifeCycleListener(BackgroundJob.this);
				if (mDialog.getWindow() != null)
					mDialog.dismiss();
			}
		};

		public BackgroundJob(BaseActivity activity, Runnable job, ProgressDialog dialog, Handler handler) {
			mActivity = activity;
			mDialog = dialog;
			mJob = job;
			mActivity.addLifeCycleListener(this);
			mHandler = handler;
		}

		public void run() {
			try {
				mJob.run();
			} finally {
				mHandler.post(mCleanupRunner);
			}
		}

		@Override
		public void onActivityDestroyed(BaseActivity activity) {
			// We get here only when the onDestroyed being called before
			// the mCleanupRunner. So, run it now and remove it from the queue
			mCleanupRunner.run();
			mHandler.removeCallbacks(mCleanupRunner);
		}

		@Override
		public void onActivityStopped(BaseActivity activity) {
			mDialog.hide();
		}

		@Override
		public void onActivityStarted(BaseActivity activity) {
			mDialog.show();
		}
	}

	private static void startBackgroundJob(BaseActivity activity, String title, String message, Runnable job,
			Handler handler) {
		// Make the progress dialog uncancelable, so that we can gurantee
		// the thread will be done before the activity getting destroyed.
		ProgressDialog dialog = ProgressDialog.show(activity, title, message, true, false);
		new Thread(new BackgroundJob(activity, job, dialog, handler)).start();
	}

	Runnable mRunFaceDetection = new Runnable() {
		float mScale = 1F;
		Matrix mImageMatrix;

		// Create a default HightlightView if we found no face in the picture.
		private void makeDefault() {
			HighlightView hv = new HighlightView(mImageView);

			int width = mBitmap.getWidth();
			int height = mBitmap.getHeight();

			Rect imageRect = new Rect(0, 0, width, height);

			// make the default size about 4/5 of the width or height
			int cropWidth = Math.min(width, height) * 4 / 5;
			int cropHeight = cropWidth;

			if (mAspectX != 0 && mAspectY != 0) {
				if (mAspectX > mAspectY) {
					cropHeight = cropWidth * mAspectY / mAspectX;
				} else {
					cropWidth = cropHeight * mAspectX / mAspectY;
				}
			}

			int x = (width - cropWidth) / 2;
			int y = (height - cropHeight) / 2;

			RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
			hv.setup(mImageMatrix, imageRect, cropRect, mCircleCrop, mAspectX != 0 && mAspectY != 0);
			mImageView.add(hv);
		}

		public void run() {
			mImageMatrix = mImageView.getImageMatrix();

			mScale = 1.0F / mScale;
			mHandler.post(new Runnable() {
				public void run() {
					makeDefault();

					mImageView.invalidate();
					if (mImageView.HighlightViews.size() == 1) {
						mCrop = mImageView.HighlightViews.get(0);
						mCrop.setFocus(true);
					}
				}
			});
		}
	};

	protected void onSaveClicked() {

		myCropHandler.sendEmptyMessage(SHOW_PROGRESSBAR);


		// TODO this code needs to change to use the decode/crop/encode single
		// step api so that we don't require that the whole (possibly large)
		// bitmap doesn't have to be read into memory
		if (mCrop == null) {
			return;
		}

		if (mSaving)
			return;
		mSaving = true;

		Bitmap croppedImage;

		// If the output is required to a specific size, create an new image
		// with the cropped image in the center and the extra space filled.
		if (mOutputX != 0 && mOutputY != 0 && !mScale) {
			// Don't scale the image but instead fill it so it's the
			// required dimension
			croppedImage = Bitmap.createBitmap(mOutputX, mOutputY, Bitmap.Config.RGB_565);
			Canvas canvas = new Canvas(croppedImage);

			Rect srcRect = mCrop.getCropRect();
			Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);

			int dx = (srcRect.width() - dstRect.width()) / 2;
			int dy = (srcRect.height() - dstRect.height()) / 2;

			// If the srcRect is too big, use the center part of it.
			srcRect.inset(Math.max(0, dx), Math.max(0, dy));

			// If the dstRect is too big, use the center part of it.
			dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

			// Draw the cropped bitmap in the center
			canvas.drawBitmap(mBitmap, srcRect, dstRect, null);

			// Release bitmap memory as soon as possible
			mImageView.clear();
			mBitmap.recycle();
		} else {
			Rect r = mCrop.getCropRect();

			int width = r.width();
			int height = r.height();

			// If we are circle cropping, we want alpha channel, which is the
			// third param here.
			croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

			Canvas canvas = new Canvas(croppedImage);
			Rect dstRect = new Rect(0, 0, width, height);
			canvas.drawBitmap(mBitmap, r, dstRect, null);

			// Release bitmap memory as soon as possible
			mImageView.clear();
			mBitmap.recycle();

			// If the required dimension is specified, scale the image.
			if (mOutputX != 0 && mOutputY != 0 && mScale) {
				croppedImage = transform(new Matrix(), croppedImage, mOutputX, mOutputY, mScaleUp, RECYCLE_INPUT);
			}
		}

		mImageView.setImageBitmapResetBase(croppedImage, true);
		mImageView.center(true, true);
		mImageView.HighlightViews.clear();

		// extras.putParcelable("data", croppedImage);
		// add image to sdcard
		strPath = getFile().getAbsolutePath();
		final Bitmap croppBitmap = croppedImage;
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				saveBitmapToSDcard(strPath, croppBitmap);
			}
		}).start();

	}

	private void saveBitmapToSDcard(String strPath, Bitmap bitmap) {
		if (bitmap == null)
			return;
		File f = new File(strPath);
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		} catch (FileNotFoundException e) {
		}
		try {
			if (fOut != null) {
				fOut.flush();
				fOut.close();
			}
			myCropHandler.sendEmptyMessage(DISS_PROGRESSBAR);
		} catch (IOException e) {
		}

	}

	private File getFile() {
		// TODO Auto-generated method stub

		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TempCropimage");
		if (!file.exists()) {
			file.mkdir();
		}
		return new File(file, "/" + getSysNowTime() + ".jpg");
	}

	@SuppressLint("SimpleDateFormat")
	private String getSysNowTime() {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
	}

	private static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, boolean scaleUp,
			boolean recycle) {
		int deltaX = source.getWidth() - targetWidth;
		int deltaY = source.getHeight() - targetHeight;
		if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
			/*
			 * In this case the bitmap is smaller, at least in one dimension,
			 * than the target. Transform it by placing as much of the image as
			 * possible into the target and leaving the top/bottom or left/right
			 * (or both) black.
			 */
			Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(b2);

			int deltaXHalf = Math.max(0, deltaX / 2);
			int deltaYHalf = Math.max(0, deltaY / 2);
			Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf + Math.min(targetWidth, source.getWidth()),
					deltaYHalf + Math.min(targetHeight, source.getHeight()));
			int dstX = (targetWidth - src.width()) / 2;
			int dstY = (targetHeight - src.height()) / 2;
			Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight - dstY);
			c.drawBitmap(source, src, dst, null);
			if (recycle) {
				source.recycle();
			}
			return b2;
		}
		float bitmapWidthF = source.getWidth();
		float bitmapHeightF = source.getHeight();

		float bitmapAspect = bitmapWidthF / bitmapHeightF;
		float viewAspect = (float) targetWidth / targetHeight;

		if (bitmapAspect > viewAspect) {
			float scale = targetHeight / bitmapHeightF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		} else {
			float scale = targetWidth / bitmapWidthF;
			if (scale < .9F || scale > 1F) {
				scaler.setScale(scale, scale);
			} else {
				scaler = null;
			}
		}

		Bitmap b1;
		if (scaler != null) {
			// this is used for minithumb and crop, so we want to filter here.
			b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
		} else {
			b1 = source;
		}

		if (recycle && b1 != source) {
			source.recycle();
		}

		int dx1 = Math.max(0, b1.getWidth() - targetWidth);
		int dy1 = Math.max(0, b1.getHeight() - targetHeight);

		Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);

		if (b2 != b1) {
			if (recycle || b1 != source) {
				b1.recycle();
			}
		}

		return b2;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}

@SuppressLint("Instantiatable")
class CropImageView extends ImageViewTouchBase {
	ArrayList<HighlightView> HighlightViews = new ArrayList<HighlightView>();
	HighlightView mMotionHighlightView = null;
	float mLastX, mLastY;
	int mMotionEdge;

	Context mContext;

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (mBitmapDisplayed.getBitmap() != null) {
			for (HighlightView hv : HighlightViews) {
				hv.mMatrix.set(getImageMatrix());
				hv.invalidate();
				if (hv.mIsFocused) {
					centerBasedOnHighlightView(hv);
				}
			}
		}
	}

	public CropImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void zoomTo(float scale, float centerX, float centerY) {
		super.zoomTo(scale, centerX, centerY);
		for (HighlightView hv : HighlightViews) {
			hv.mMatrix.set(getImageMatrix());
			hv.invalidate();
		}
	}

	@Override
	protected void zoomIn() {
		super.zoomIn();
		for (HighlightView hv : HighlightViews) {
			hv.mMatrix.set(getImageMatrix());
			hv.invalidate();
		}
	}

	@Override
	protected void zoomOut() {
		super.zoomOut();
		for (HighlightView hv : HighlightViews) {
			hv.mMatrix.set(getImageMatrix());
			hv.invalidate();
		}
	}

	@Override
	protected void postTranslate(float deltaX, float deltaY) {
		super.postTranslate(deltaX, deltaY);
		for (int i = 0; i < HighlightViews.size(); i++) {
			HighlightView hv = HighlightViews.get(i);
			hv.mMatrix.postTranslate(deltaX, deltaY);
			hv.invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		BaseCropActivity cropImage = (BaseCropActivity) mContext;
		if (cropImage.mSaving) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < HighlightViews.size(); i++) {
				HighlightView hv = HighlightViews.get(i);
				int edge = hv.getHit(event.getX(), event.getY());
				if (edge != HighlightView.GROW_NONE) {
					mMotionEdge = edge;
					mMotionHighlightView = hv;
					mLastX = event.getX();
					mLastY = event.getY();
					mMotionHighlightView.setMode((edge == HighlightView.MOVE) ? HighlightView.ModifyMode.Move
							: HighlightView.ModifyMode.Grow);
					break;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mMotionHighlightView != null) {
				centerBasedOnHighlightView(mMotionHighlightView);
				mMotionHighlightView.setMode(HighlightView.ModifyMode.None);
			}
			mMotionHighlightView = null;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mMotionHighlightView != null) {
				mMotionHighlightView.handleMotion(mMotionEdge, event.getX() - mLastX, event.getY() - mLastY);
				mLastX = event.getX();
				mLastY = event.getY();

				if (true) {
					// This section of code is optional. It has some user
					// benefit in that moving the crop rectangle against
					// the edge of the screen causes scrolling but it means
					// that the crop rectangle is no longer fixed under
					// the user's finger.
					ensureVisible(mMotionHighlightView);
				}
			}
			break;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			center(true, true);
			break;
		case MotionEvent.ACTION_MOVE:
			// if we're not zoomed then there's no point in even allowing
			// the user to move the image around. This call to center puts
			// it back to the normalized location (with false meaning don't
			// animate).
			if (getScale() == 1F) {
				center(true, true);
			}
			break;
		}

		return true;
	}

	// Pan the displayed image to make sure the cropping rectangle is visible.
	private void ensureVisible(HighlightView hv) {
		Rect r = hv.mDrawRect;

		int panDeltaX1 = Math.max(0, getLeft() - r.left);
		int panDeltaX2 = Math.min(0, getRight() - r.right);

		int panDeltaY1 = Math.max(0, getTop() - r.top);
		int panDeltaY2 = Math.min(0, getBottom() - r.bottom);

		int panDeltaX = panDeltaX1 != 0 ? panDeltaX1 : panDeltaX2;
		int panDeltaY = panDeltaY1 != 0 ? panDeltaY1 : panDeltaY2;

		if (panDeltaX != 0 || panDeltaY != 0) {
			panBy(panDeltaX, panDeltaY);
		}
	}

	// If the cropping rectangle's size changed significantly, change the
	// view's center and scale according to the cropping rectangle.
	private void centerBasedOnHighlightView(HighlightView hv) {
		Rect drawRect = hv.mDrawRect;

		float width = drawRect.width();
		float height = drawRect.height();

		float thisWidth = getWidth();
		float thisHeight = getHeight();

		float z1 = thisWidth / width * .6F;
		float z2 = thisHeight / height * .6F;

		float zoom = Math.min(z1, z2);
		zoom = zoom * this.getScale();
		zoom = Math.max(1F, zoom);

		if ((Math.abs(zoom - getScale()) / zoom) > .1) {
			float[] coordinates = new float[] { hv.mCropRect.centerX(), hv.mCropRect.centerY() };
			getImageMatrix().mapPoints(coordinates);
			zoomTo(zoom, coordinates[0], coordinates[1], 300F);
		}

		ensureVisible(hv);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < HighlightViews.size(); i++) {
			HighlightViews.get(i).draw(canvas);
		}
	}

	public void add(HighlightView hv) {
		HighlightViews.add(hv);
		invalidate();
	}


}
