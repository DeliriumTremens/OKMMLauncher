package com.okmm.alert.vo.bean;

import com.okmm.alert.vo.AbstractVO;

public class Statics extends AbstractVO {

  private static final long serialVersionUID = 5397077798316510L;
  
  private Integer campaignId = null;
  private Integer actionId = null;
  private Integer typeId = null;
  
  public Integer getCampaignId() {
	return campaignId;
  }
  public void setCampaignId(Integer campaignId) {
	this.campaignId = campaignId;
  }
  public Integer getActionId() {
	return actionId;
  }
  public void setActionId(Integer actionId) {
	this.actionId = actionId;
  }
  public Integer getTypeId() {
	return typeId;
  }
  public void setTypeId(Integer typeId) {
	this.typeId = typeId;
  }
  
}
