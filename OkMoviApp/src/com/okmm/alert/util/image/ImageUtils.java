package com.okmm.alert.util.image;

import java.io.ByteArrayOutputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
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
  
  
}
