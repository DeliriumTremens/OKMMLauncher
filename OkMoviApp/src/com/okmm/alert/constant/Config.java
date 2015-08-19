package com.okmm.alert.constant;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Environment;

import com.okmm.alert.db.dao.core.CampaignDAO;
import com.okmm.alert.db.dao.core.StaticsDAO;

public class Config {
	
  public static final String LOG_TAG = "okmm";
  public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory() + "/.okmm";
  public static final String CACHE_LOCAL_PATH = BASE_LOCAL_PATH + "/cache";
  public static final String BACKUP_LOCAL_PATH = BASE_LOCAL_PATH + "/bk/";
  
  public static final String WP_LAST_FILE_NAME = "last";
  public static final String WP_CURRENT_FILE_NAME = "current";
  
  public static final String IMG_BASE_PATH = "http://blackboxtech.mx/ok-movi/";  
  public static final String WS_BASE_PATH = "http://blackboxtech.mx/ok-movi/services/";
  public static final String IMG_LOCAL_PATH = BASE_LOCAL_PATH + "/img";
  public static final String WS_METHOD_POSTFIX = ".php";
  public static final String WS_STATUS_OK = "0";
  public static final String MESSAGE_CODE_PREFFIX = "message_";
  
  public static final String ERR_CODE_UNAVAILABLE = "8";  
  
  //TODO
  public static final Integer KEEP_ALIVE_TIMER = 1000 * 60 *60 *24;
  public static final Integer AUTH_TIMER = 5000;
  public static final Integer LOADER_TIMER = 1000 * 60 * 1;
  public static final Integer LOADER_PERIOD = 1000 * 60 * 3;
  public static final Integer DISPLAYER_TIMER = 1000 * 60 * 1;
  public static final Integer UNAVAILABLE_TIMER = 1000 * 30;
  public static final Integer UNAVAILABLE_PERIOD_DAYS = 7;
  
  public static final Integer TIME_TO_CLOSE = 3000;
  public final static String SQL_SCRIPT_SEPARATOR = "--sentence";
  public final static String DEFAULT_TABLE_ID_FIELD = "_ID";
  public static final String DATABASE_NAME = "okmoviDB";
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_SCRIPT_CREATE_LOCATION = "db/db_create.sql";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
  public static final Integer URL_CONNECTION_CONNECT_TIMEOUT = 3000;
  public static final Integer URL_CONNECTION_READ_TIMEOUT = 3000;
  public static final Integer IMG_SCALE = 1;
  public static final int MAX_IMAGE_SIZE = 180;
  
  @SuppressWarnings("serial")
  public static final Map<Class <?> , String> DAO_TABLE_MAPPING = new HashMap<Class <?>, String>() {{
	                                     put(CampaignDAO.class, "CAMPAIGN");
	                                     put(StaticsDAO.class, "STATICS");
  }};
  
  public static enum CAMPAIGN_STATUS {
	NEW(1), DISPLAYED(2), DONE(3);
		
	private Integer id;
		    
	private CAMPAIGN_STATUS(Integer id) {
	  this.id = id;
	}
		 
	public Integer getId() {
	  return id;
	}
  }
  
  public static enum ACTION_ID {
	SHOW(1), CLOSED(2), LINKED(3), CHANGE(3);
			
	private Integer id;
			    
	private ACTION_ID(Integer id) {
	  this.id = id;
	}
			 
	public Integer getId() {
	  return id;
	}
  }
  
  public static enum ELEMENT_TYPE {
	POPUP(1), WALLPER(2), LOCK(3);
				
	private Integer id;
				    
	private ELEMENT_TYPE(Integer id) {
	  this.id = id;
	}
				 
	public Integer getId() {
	  return id;
	}
  }

}
