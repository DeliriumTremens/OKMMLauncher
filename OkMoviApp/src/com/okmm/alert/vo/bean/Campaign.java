package com.okmm.alert.vo.bean;

import com.okmm.alert.vo.AbstractVO;

public class Campaign extends AbstractVO {

  private static final long serialVersionUID = -4806988920134562490L;
  
  private String popup = null;
  private String background = null;
  private String lockscreen = null;
  
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
  
}
