package com.okmm.alert.db.dao.core;

import java.util.ArrayList;
import java.util.List;

import com.okmm.alert.constant.Config;
import com.okmm.alert.db.dao.AbstractDAO;
import com.okmm.alert.vo.bean.Statics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class StaticsDAO extends AbstractDAO<Statics> {
  
  public StaticsDAO (Context ctx){
	super(ctx);
  }
	
  @Override
  public List<Statics> parse(Cursor result){
	List<Statics> campaigns = new ArrayList<Statics> ();
	Statics campaign = null;
    try {
    	if(result.moveToFirst()){
    	  do{
    		 campaign = new Statics();
    		 campaign.setId(result.getInt(0));
    		 campaign.setCampaignId(result.getInt(1));
    		 campaign.setActionId(result.getInt(2));
    		 campaign.setTypeId(result.getInt(3));
    		 campaigns.add(campaign);
    	  } while (result.moveToNext());
    	}	
	} finally {
		result.close();
	}
    return campaigns;
  }
  
  @Override
  public ContentValues convert (Statics Statics){
    ContentValues values = new ContentValues();
	values.put(Config.DEFAULT_TABLE_ID_FIELD, Statics.getId());
    values.put("CAMPAIGN_ID", Statics.getCampaignId());
    values.put("ACTION_ID", Statics.getActionId());
    values.put("TYPE_ID", Statics.getTypeId());
	return values;
  }
  
  public final Statics find(Integer campaignId, Integer typeId){
    StringBuilder sqlQuery = new StringBuilder ();	  
	sqlQuery.append("SELECT * FROM " + tableName)
		    .append(" WHERE CAMPAIGN_ID = ? AND TYPE_ID = ?");
	return queryForObject(sqlQuery, campaignId, typeId);
 }
  
}
