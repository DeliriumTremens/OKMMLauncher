package com.okmm.alert.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.okmm.alert.constant.Config;
import com.okmm.alert.vo.bean.Campaign;

public class JsonUtil {

  public static Campaign getCampaign(JSONObject jsonObject) throws JSONException {
	Campaign campaign = new Campaign();
	campaign.setId(jsonObject.getInt("id_camp"));
	if(!jsonObject.getString("pop_up").isEmpty()){
	  campaign.setPopup(Config.IMG_BASE_PATH + jsonObject.getString("pop_up"));
	}
	if(!jsonObject.getString("background").isEmpty()){
	  campaign.setBackground(Config.IMG_BASE_PATH + jsonObject.getString("background"));
	}
	campaign.setLockscreen(Config.IMG_BASE_PATH + jsonObject.getString("lockscreen"));
	campaign.setLink(jsonObject.getString("link"));
	return campaign;
  }
  
}
