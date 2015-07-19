package com.okmm.alert.vo.bean;

import java.util.Date;

import com.okmm.alert.vo.AbstractVO;

public class Campaign extends AbstractVO {

  private static final long serialVersionUID = -4806988920134562490L;
  
  private String popup = null;
  private String background = null;
  private String lockscreen = null;
  private String link = null;
  private Date loadedDate = null;
  private Integer status = null;
  
  public String getPopup() {
	return popup;
  }
  public void setPopup(String popup) {
	this.popup = popup;
  }
  public String getBackground() {
	return background;
  }
  public void setBackground(String background) {
	this.background = background;
  }
  public String getLockscreen() {
	return lockscreen;
  }
  public void setLockscreen(String lockscreen) {
	this.lockscreen = lockscreen;
  }
  public Date getLoadedDate() {
	return loadedDate;
  }
  public void setLoadedDate(Date loadedDate) {
	this.loadedDate = loadedDate;
  }
  public String getLink() {
	return link;
  }
  public void setLink(String link) {
	this.link = link;
  }
  public Integer getStatus() {
	return status;
  }
  public void setStatus(Integer status) {
	this.status = status;
  }
  
}
