package com.okmm.alert.constant;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.os.Environment;

import com.okmm.alert.db.dao.core.CampaignDAO;

public class Config {
	
  public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory() + "/.okmm";
  public static final String CACHE_LOCAL_PATH = BASE_LOCAL_PATH + "/cache";
  
  public static final String IMG_BASE_PATH = "http://blackboxtech.mx/ok-movi/";  
  public static final String WS_BASE_PATH = "http://blackboxtech.mx/ok-movi/services/";
  public static final String IMG_LOCAL_PATH = BASE_LOCAL_PATH + "/img";
  public static final String WS_METHOD_POSTFIX = ".php";
  public static final String WS_STATUS_OK = "0";
  public static final String MESSAGE_CODE_PREFFIX = "message_";
  //TODO
  //public static final Integer TIMER_LAP = 1000 * 60 * 30;
  //public static final Integer DISPLAYER_TIMER = 1000 * 60 * 15;
  public static final Integer LOADER_TIMER = 1000 * 30;
  public final static String SQL_SCRIPT_SEPARATOR = "--sentence";
  public final static String DEFAULT_TABLE_ID_FIELD = "_ID";
  public static final String DATABASE_NAME = "okmoviDB";
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_SCRIPT_CREATE_LOCATION = "db/db_create.sql";
  public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
  public static final Integer URL_CONNECTION_CONNECT_TIMEOUT = 3000;
  public static final Integer URL_CONNECTION_READ_TIMEOUT = 3000;
  public static final Integer IMG_SCALE = 1;
  public static final int MAX_IMAGE_SIZE = 180;
  
  @SuppressWarnings("serial")
  public static final Map<Class <?> , String> DAO_TABLE_MAPPING = new HashMap<Class <?>, String>() {{
	                                     put(CampaignDAO.class, "CAMPAIGN");
  }};

}
