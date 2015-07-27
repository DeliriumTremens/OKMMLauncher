package com.okmm.alert.util.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.okmm.alert.constant.Config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images;

public class ImageUtils {
  
  
  public static Bitmap getResizedBitmap(Bitmap bm, int size) {
    int width = bm.getWidth();
    int height = bm.getHeight();
    float scaleWidth = ((float) size) / width;
    float scaleHeight = ((float) size) / height;
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    return resizedBitmap;
  }
  
  public static Bitmap rotate(Context ctx, Bitmap bm, String filePath) {
	try {
	     ExifInterface exif = new ExifInterface(filePath);
	     Matrix mat = new Matrix();
	     int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION
	        		                          , ExifInterface.ORIENTATION_NORMAL);
	     int angle = 0;
	     switch (orientation) {
	       case ExifInterface.ORIENTATION_ROTATE_90:
	            angle = 90;
	            break;
	       case ExifInterface.ORIENTATION_ROTATE_180:
	            angle = 180;
	            break;
	       case ExifInterface.ORIENTATION_ROTATE_270:
	            angle = 270;
	            break;
	       default:
	            angle = 0;
	            break;
	      }
	      mat.postRotate(angle);
	      return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight()
	        		                                           , mat, true);
	} catch (Exception e) {
	    //e.printStackTrace();
	}
	return null;	  
  }

  public static Uri getImageUri(Context inContext, Bitmap inImage) {
	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
	String path = Images.Media.insertImage(inContext.getContentResolver()
			                                   , inImage, "Title", null);
	return Uri.parse(path);
  }
  
  public static boolean isWebUri(Uri uri){
	boolean isWeb = false;
	if(uri != null){
	  isWeb = uri.toString().startsWith("http:/");
	}
	return isWeb;
  }
  
  public static void drawableTofile(Drawable drawable, String fileName){
	bitmapToFile(drawableToBitmap(drawable, fileName), fileName);
  }
  
  public static Bitmap drawableToBitmap (Drawable drawable, String fileName) {
	if (drawable instanceof BitmapDrawable) {
	  return ((BitmapDrawable)drawable).getBitmap();
	}
	int width = drawable.getIntrinsicWidth();
	width = width > 0 ? width : 1;
	int height = drawable.getIntrinsicHeight();
	height = height > 0 ? height : 1;
	Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(bitmap); 
	drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	drawable.draw(canvas);
	return bitmap;
  }
  
  private static void bitmapToFile(Bitmap bm, String fileName){
	File dest = new File(Config.BACKUP_LOCAL_PATH + fileName);
	try {
	    FileOutputStream out = new FileOutputStream(dest);
	    bm.compress(Bitmap.CompressFormat.PNG, 90, out);
	    out.flush();
	    out.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
  }
  
  
}
