package com.okmm.alert.db.dao.core;

import java.util.ArrayList;
import java.util.List;







import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.AbstractDAO;
import com.okmm.alert.util.Utilities;
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
	List<Campaign> campaigns = new ArrayList<Campaign> ();
	Campaign campaign = null;
    try {
    	if(result.moveToFirst()){
    	  do{
    		 campaign = new Campaign();
    		 campaign.setId(result.getInt(0));
    		 campaign.setPopup(result.getString(1));
    		 campaign.setBackground(result.getString(2));
    		 campaign.setLockscreen(result.getString(3));
    		 campaign.setLoadedDate(Utilities.stringToDate(result.getString(4)));
    		 campaign.setStatus(result.getInt(5));
    		 campaigns.add(campaign);
    	  } while (result.moveToNext());
    	}	
	} finally {
		result.close();
	}
    return campaigns;
  }
  
  @Override
  public ContentValues convert (Campaign Campaign){
    ContentValues values = new ContentValues();
	values.put(Config.DEFAULT_TABLE_ID_FIELD, Campaign.getId());
    values.put("POP_UP", Campaign.getPopup());
    values.put("BACKGROUND", Campaign.getBackground());
    values.put("LOCK_SCREEN", Campaign.getLockscreen());
    values.put("LOADED_DATE", Utilities.dateToString(Campaign.getLoadedDate()));
    values.put("STATUS", Campaign.getStatus());
	return values;
  }
  
  public final Campaign findActive(){
	StringBuilder sqlQuery = new StringBuilder ();	  
	sqlQuery.append("SELECT * FROM " + tableName)
		    .append(" WHERE STATUS = ? OR STATUS = ?")
		    .append(" LIMIT 1");
	return queryForObject(sqlQuery, Config.CAMPAIGN_STATUS.DISPLAYED.getId()
			                         ,  Config.CAMPAIGN_STATUS.NEW.getId());
  }
  
}
