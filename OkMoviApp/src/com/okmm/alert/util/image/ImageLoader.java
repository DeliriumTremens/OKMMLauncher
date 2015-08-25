package com.okmm.alert.util.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.okmm.alert.constant.Config;
import com.okmm.alert.util.Utilities;
import com.okmm.alert.vo.bean.Campaign;

import android.content.Context;
  
public class ImageLoader {
  
  private FileCache fileCache;
  
  
  public ImageLoader(Context context){
    fileCache=new FileCache(context);
  }
  
  public void clearCache() {
    fileCache.clear();
  }
  
  public void setFiles(Campaign campaign){
	File file = null;
	if(campaign.getBackground() != null && ! campaign.getBackground().isEmpty()){
	  file = getFile(campaign.getBackground());
	  campaign.setBackground(file.getAbsolutePath());
	}
	if(campaign.getPopup() != null && ! campaign.getPopup().isEmpty()){
	  file = getFile(campaign.getPopup());
	  campaign.setPopup(file.getAbsolutePath());
	}
	if(campaign.getLockscreen() != null && ! campaign.getLockscreen().isEmpty()){
	  file = getFile(campaign.getLockscreen());
	  campaign.setLockscreen(file.getAbsolutePath());
	}
  }
  
  private File getFile(String url){
	File f=fileCache.getFile(url);
	HttpURLConnection conn = null;
	InputStream is=null;
	OutputStream os = null;
	try {
	     conn = getHttpConnection(url);
	     is=conn.getInputStream();
	     os = new FileOutputStream(f);
	     Utilities.CopyStream(is, os);
	     os.close();
	} catch (Exception ex){
		ex.printStackTrace();
	     return null;
	} finally{
	    try{
	    	is.close();
	    	conn.disconnect();
	    } catch(Exception ignored){}
	}
	return f;
  }
  
  public File getFileFromCache(String url){
	return fileCache.getFile(url);
  }
	  
  private HttpURLConnection getHttpConnection(String url) throws IOException{
	HttpURLConnection conn = null;
	URL imageUrl = null;
	imageUrl = new URL(url);
	conn = (HttpURLConnection)imageUrl.openConnection();
	if(conn.getHeaderField("Location") != null){
	  imageUrl = new URL(conn.getHeaderField("Location"));
	  conn.disconnect();
	  conn = (HttpURLConnection) imageUrl.openConnection();
	}
	conn.setConnectTimeout(Config.URL_CONNECTION_CONNECT_TIMEOUT);
	conn.setReadTimeout(Config.URL_CONNECTION_READ_TIMEOUT);
	conn.setInstanceFollowRedirects(false);
	return conn;
  }
  
}
