package com.okmm.alert.util.image;

import java.io.File;



import com.okmm.alert.constant.Config;

import android.content.Context;
  
public class FileCache {
  
  private File cacheDir;
  
  public FileCache(Context context){
    if(android.os.Environment.getExternalStorageState().equals(android.os
    		                                 .Environment.MEDIA_MOUNTED)){
       cacheDir=new File(Config.CACHE_LOCAL_PATH);
    } else {
       cacheDir=context.getCacheDir();
    }
    if(!cacheDir.exists()){
            cacheDir.mkdirs();
    }
  }
  
  public File getFile(String url){
    String filename=String.valueOf(url.hashCode());
    File f = new File(cacheDir, filename);
    return f;
  }
  
  public void clear(){
    File[] files=cacheDir.listFiles();
    if(files==null){
      return;
    }
    for(File f:files){
      f.delete();
    }
  }
  
}