package com.okmm.alert.db.dao.core;

import java.util.ArrayList;
import java.util.List;





import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.AbstractDAO;
import com.okmm.alert.vo.bean.Campaign;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class CampaignDAO extends AbstractDAO<Campaign> {
  
  public CampaignDAO (Context ctx){
	super(ctx);
  }
	
  @Override
  public List<Campaign> parse(Cursor result){
	List<Campaign> Campaigns = new ArrayList<Campaign> ();
	Campaign Campaign = null;
    try {
    	if(result.moveToFirst()){
    	  do{
    		 Campaign = new Campaign();
    		 Campaign.setId(result.getInt(0));
    		 Campaign.setPopup(result.getString(1));
    		 Campaign.setBackground(result.getString(2));
    		 Campaign.setLockscreen(result.getString(3));
    		 Campaigns.add(Campaign);
    	  } while (result.moveToNext());
    	}	
	} finally {
		result.close();
	}
    return Campaigns;
  }
  
  @Override
  public ContentValues convert (Campaign Campaign){
    ContentValues values = new ContentValues();
	values.put(Config.DEFAULT_TABLE_ID_FIELD, Campaign.getId());
    values.put("POP_UP", Campaign.getPopup());
    values.put("BACKGROUND", Campaign.getBackground());
    values.put("LOCK_SCREEN", Campaign.getLockscreen());
	return values;
  }
  
}
