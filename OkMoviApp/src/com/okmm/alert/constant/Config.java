package com.okmm.alert.constant;

import java.util.HashMap;
import java.util.Map;

public class Config {
	
  public static final String WS_BASE_PATH = "http://blackboxtech.mx/ok-movi/services/";
  public static final String WS_METHOD_POSTFIX = ".php";
  public static final String WS_STATUS_OK = "0";
  public static final String MESSAGE_CODE_PREFFIX = "message_";
  public static final Integer TIMER_LAP = 1000 * 60 * 30;
  public final static String SQL_SCRIPT_SEPARATOR = "--sentence";
  public final static String DEFAULT_TABLE_ID_FIELD = "_ID";
  public static final String DATABASE_NAME = "okmoviDB";
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_SCRIPT_CREATE_LOCATION = "db/db_create.sql";
  
  @SuppressWarnings("serial")
  public static final Map<Class <?> , String> DAO_TABLE_MAPPING = new HashMap<Class <?>, String>() {{
  }};

}
