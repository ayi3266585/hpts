package com.queqianme.hpt.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ab.util.AbFileUtil;
import com.ab.util.AbImageUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.cropimage.CropImage;
import com.ab.view.cropimage.CropImageView;
import com.queqianme.hpt.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 裁剪界面
 */
public class CropImageActivity extends Activity implements OnClickListener{
	private static final String TAG = "CropImageActivity";
	//private static final boolean D = Constant.DEBUG;
	private CropImageView mImageView;
	private Bitmap mBitmap;
	
	private CropImage mCrop;
	static String path;
	private Button mSave;
	private Button mCancel,rotateLeft,rotateRight;
	private String mPath = "CropImageActivity";
	public int screenWidth = 0;
	public int screenHeight = 0;
	static Intent intent = new Intent();
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				break;
			
			}

		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_image);
        init();
    }
    @Override
    protected void onStop(){
    	super.onStop();
    	if(mBitmap!=null){
    		mBitmap=null;
    	}
    }
    
    private void init(){
    	getWindowWH();
    	mPath = getIntent().getStringExtra("PATH");
    //	if(D)Log.d(TAG, "将要进行裁剪的图片的路径是 = " + mPath);
        mImageView = (CropImageView) findViewById(R.id.crop_image);
        mSave = (Button) this.findViewById(R.id.okBtn);
        mCancel = (Button) this.findViewById(R.id.cancelBtn);
        rotateLeft = (Button) this.findViewById(R.id.rotateLeft);
        rotateRight = (Button) this.findViewById(R.id.rotateRight);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        rotateLeft.setOnClickListener(this);
        rotateRight.setOnClickListener(this);
        //相册中原来的图片
        File mFile = new File(mPath);
        try{
        	mBitmap = AbFileUtil.getBitmapFromSD(mFile,AbImageUtil.SCALEIMG,1000,1000);
            if(mBitmap==null){
                AbToastUtil.showToast(CropImageActivity.this, "没有找到图片");
    			finish();
            }else{
            //	intent.putExtra("data", mBitmap);
            	resetImageView(mBitmap);
            }
        }catch (Exception e) {
            AbToastUtil.showToast(CropImageActivity.this, "没有找到图片");
			finish();
		}
    }
    /**
     * 获取屏幕的高和宽
     */
    private void getWindowWH(){
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}
    private void resetImageView(Bitmap b){
    	 mImageView.clear();
    	 mImageView.setImageBitmap(b);
         mImageView.setImageBitmapResetBase(b, true);
         mCrop = new CropImage(this, mImageView,mHandler);
         mCrop.crop(b);
    }

	/**
	 * 点击事件监听
	 */
    public void onClick(View v){
    	switch (v.getId()){
    	case R.id.cancelBtn:
    		finish();
    		break;
    	case R.id.okBtn:
    	   try{
    	//	path = mCrop.saveToLocal(mCrop.cropAndSave());
    		saveImageToGallery(CropImageActivity.this,compressImage(mCrop.cropAndSave()));
    	//	if(D) Log.i(TAG, "裁剪后图片的路径是 = " + path);
    		System.out.println("path:"+path);
    		intent.putExtra("PATH", path);
    		 
    //		intent.putExtra("data", mCrop.cropAndSave());
    		setResult(RESULT_OK, intent);
    	   }catch (Exception e) {
			// TODO: handle exception
    	//	   saveImageToGallery(CropImageActivity.this,mCrop.cropAndSave());
    	   }
    		finish();
    		break;
    	case R.id.rotateLeft:
    		mCrop.startRotate(270.f);
    		break;
    	case R.id.rotateRight:
    		mCrop.startRotate(90.f);
    		break;
    		
    	}
    }
    /**
     * 保存图片类
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
    
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
    	}
        
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
    				file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        path=file.getPath();
		// 最后通知图库更新
        Bitmap bm = BitmapFactory.decodeFile(path);  
        intent.putExtra("data", getSmallBitmap(path));
     //   intent.putExtra("PATH2", file.getPath());
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,	Uri.fromFile(new File(file.getPath()))));
    }
    
    /**
     * 图片压缩类
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, 800, 800);

        // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;

    return BitmapFactory.decodeFile(filePath, options);
    }
  /**
   * 计算图片的缩放值
   */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
                 final int heightRatio = Math.round((float) height/ (float) reqHeight);
                 final int widthRatio = Math.round((float) width / (float) reqWidth);
                 inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
            return inSampleSize;
    }
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>1000) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩        
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            image.compress(CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    private static Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();        
        image.compress(CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
            baos.reset();//重置baos即清空baos
            image.compress(CompressFormat.JPEG, 100, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是1024*768分辨率，所以高和宽我们设置为
        float hh = 1024f;//这里设置高度为800f
        float ww = 768f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
      //  newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return bitmap;
        //		compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
    
    
    
    
    
    
}
