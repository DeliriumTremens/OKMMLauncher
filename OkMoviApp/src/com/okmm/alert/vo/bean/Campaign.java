package com.okmm.alert.vo.bean;

import java.util.Date;

import com.okmm.alert.vo.AbstractVO;

public class Campaign extends AbstractVO {

  private static final long serialVersionUID = -4806988920134562490L;
  
  private String popup = null;
  private String background = null;
  private String lockscreen = null;
  private Date loadedDate = null;
  private Boolean watched = null;
  
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
  public Boolean getWatched() {
	return watched;
  }
  public void setWatched(Boolean watched) {
	this.watched = watched;
  }
  
}
