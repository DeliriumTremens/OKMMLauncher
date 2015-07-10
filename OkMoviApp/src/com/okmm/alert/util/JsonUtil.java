package com.okmm.alert.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.okmm.alert.vo.bean.Campaign;

public class JsonUtil {

  public static Campaign getCampaign(JSONObject jsonObject) throws JSONException {
	Campaign campaign = new Campaign();
	campaign.setId(jsonObject.getInt("id_camp"));
	campaign.setPopup(jsonObject.getString("pop_up"));
	campaign.setBackground(jsonObject.getString("background"));
	campaign.setLockscreen(jsonObject.getString("lockscreen"));
	return campaign;
  }
  
}
