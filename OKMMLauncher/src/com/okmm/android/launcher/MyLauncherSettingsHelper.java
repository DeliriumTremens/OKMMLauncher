package com.okmm.android.launcher;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.okmm.android.R;
import com.okmm.android.R.bool;
import com.okmm.android.R.drawable;
import com.okmm.android.R.integer;
import com.okmm.android.R.string;
import com.okmm.android.util.ToastBuilder;
import com.okmm.android.util.ws.RestClient;
import com.okmm.android.util.ws.RestResponseHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public final class MyLauncherSettingsHelper {
	public static final int ORIENTATION_SENSOR=1;
	public static final int ORIENTATION_NOSENSOR=2;
	public static final int ORIENTATION_LANDSCAPE=3;
	public static final int ORIENTATION_PORTRAIT=4;

	public static final int CACHE_LOW=1;
	public static final int CACHE_AUTO=2;
	public static final int CACHE_DISABLED=3;

	private static final String LEGACY_PREFERENCES = "launcher.preferences.okmovimovi";

	// modification of these keys cause restart of the entire launcher... 
	private static final String[] restart_keys = {
		"highlights_color",
		"highlights_color_focus",
		"uiNewSelectors",
		"uiDesktopIndicatorType",
		"screenCache",
		"uiDesktopIndicator",
		"themePackageName",
		"themeIcons", 
		"notifSize",
		"drawerNew",
		"drawerStyle", 
		"drawerLabelTextSize",
		"drawerLabelBold",
		"drawerLabelColorOverride",
		"drawerLabelColor",
		"uiDesktopIndicatorColor",
		"uiDesktopIndicator",
		"desktopRows",
		"desktopColumns",
		"autosizeIcons",
		"uiHideLabels",
		"desktopLabelSize",
		"desktopLabelBold",
		"desktopLabelPaddingOverride",
		"desktopLabelPaddingH",
		"desktopLabelPaddingV",
		"desktopLabelPaddingRadius",
		"desktopLabelColorOverride",
		"desktopLabelColorText",
		"desktopLabelColorShadow",
		"desktopLabelColorBg",
		"folderTextSize",
		"drawerCatalogFlingNavigate",
		"mainDockDrawerHide"
	};

	public static boolean needsRestart(String key){
		for(int i=0;i<restart_keys.length;i++) {
			if(restart_keys[i].equals(key)) {
				return true;
			}
		}
		return false;
	}
	public static int getDesktopScreens(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("desktopScreens", context.getResources().getInteger(R.integer.config_desktops))+1;
		return screens;
	}
	public static int getDefaultScreen(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int def_screen = sp.getInt("defaultScreen", context.getResources().getInteger(R.integer.config_default_screen));
		return def_screen;
	}
	public static int getPageHorizontalMargin(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("pageHorizontalMargin", context.getResources().getInteger(R.integer.config_page_horizontal_margin));
		return newD;
	}
	public static int getColumnsPortrait(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("drawerColumnsPortrait", context.getResources().getInteger(R.integer.config_drawer_columns_portrait))+1;
		return screens;
	}
	public static int getRowsPortrait(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("drawerRowsPortrait", context.getResources().getInteger(R.integer.config_drawer_rows_portrait))+1;
		return screens;
	}
	public static int getColumnsLandscape(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("drawerColumnsLandscape", context.getResources().getInteger(R.integer.config_drawer_columns_landscape))+1;
		return screens;
	}
	public static int getRowsLandscape(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("drawerRowsLandscape", context.getResources().getInteger(R.integer.config_drawer_rows_landscape))+1;
		return screens;
	}
	public static boolean getDrawerAnimated(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean animated = sp.getBoolean("drawerAnimated", context.getResources().getBoolean(R.bool.config_drawer_animated));
		return animated;
	}
	public static boolean getHideStatusbar(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("hideStatusbar", context.getResources().getBoolean(R.bool.config_hide_statusbar));
		return newD;
	}
	public static boolean getPreviewsNew(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("previewsNew", context.getResources().getBoolean(R.bool.config_previews_spread));
		return newD;
	}
	public static int getHomeBinding(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("homeBinding", context.getResources().getString(R.string.config_home_binding_actions)));
		return newD;
	}
	public static boolean getUIDots(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiDots", context.getResources().getBoolean(R.bool.config_ui_dots));
		return newD;
	}
	public static boolean getFolderAnimate(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("folderAnimate", context.getResources().getBoolean(R.bool.config_folder_animate));
		return newD;
	}
	public static boolean getUICloseFolder(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiCloseFolder", context.getResources().getBoolean(R.bool.config_ui_close_folder));
		return newD;
	}
	public static int getFolderTextSize(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("folderTextSize", context.getResources().getInteger(R.integer.config_folder_text_size))+8;
		return newD;
	}

	public static int getDesktopSpeed(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("desktopSpeed", context.getResources().getInteger(R.integer.config_desktop_speed));
		return newD;
	}
	public static int getDesktopBounce(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("desktopBounce", context.getResources().getInteger(R.integer.config_desktop_bounce));
		return newD;
	}
	public static int getDesktopSnap(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("desktopSnap", context.getResources().getInteger(R.integer.config_desktop_snap));
		return newD;
	}

	public static boolean getDesktopLooping(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("desktopLooping", context.getResources().getBoolean(R.bool.config_desktop_looping));
		return newD;
	}
	public static boolean getUIABBg(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiABBg", context.getResources().getBoolean(R.bool.config_ui_ab_hide_bg));
		return newD;
	}

	public static int getDrawerSpeed(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("drawerSpeed", context.getResources().getInteger(R.integer.config_drawer_speed));
		return newD;
	}
	public static int getDrawerSnap(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("drawerSnap", context.getResources().getInteger(R.integer.config_drawer_snap));
		return newD;
	}
	public static boolean getDrawerOvershoot(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerOvershoot", context.getResources().getBoolean(R.bool.config_drawer_overshoot));
		return newD;
	}

	public static int getDrawerAnimationSpeed(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("animationSpeed", context.getResources().getInteger(R.integer.config_animation_speed))+300;
		return newD;
	}
	public static float getuiScaleAB(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("uiScaleAB", context.getResources().getInteger(R.integer.config_ui_ab_scale))+1;
		float scale=newD/10f;
		return scale;
	}
	public static boolean getUIHideLabels(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiHideLabels", context.getResources().getBoolean(R.bool.config_ui_hide_labels));
		return newD;
	}
	public static boolean getWallpaperHack(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("wallpaperHack", context.getResources().getBoolean(R.bool.config_wallpaper_hack));
		return newD;
	}
	public static int getHighlightsColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("highlights_color", context.getResources().getInteger(R.integer.config_highlights_color));
		return newD;
	}
	public static int getHighlightsColorFocus(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("highlights_color_focus", context.getResources().getInteger(R.integer.config_highlights_color_focus));
		return newD;
	}
	public static boolean getUINewSelectors(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiNewSelectors", context.getResources().getBoolean(R.bool.config_new_selectors));
		return newD;
	}
	public static int getDrawerColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("drawer_color", context.getResources().getInteger(R.integer.config_drawer_color));
		return newD;
	}
	public static int getDesktopColumns(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("desktopColumns", context.getResources().getInteger(R.integer.config_desktop_columns))+3;
		return screens;
	}
	public static int getDesktopRows(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int screens = sp.getInt("desktopRows", context.getResources().getInteger(R.integer.config_desktop_rows))+3;
		return screens;
	}
	public static int getDesktopLabelSize(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelSize", context.getResources().getInteger(R.integer.config_desktop_label_text_size))+8;
		return value;
	}
	public static boolean getDesktopLabelBold(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("desktopLabelBold", context.getResources().getBoolean(R.bool.config_desktop_label_bold_text));
		return newD;
	}

	public static boolean getDesktopLabelPaddingOverride(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("desktopLabelPaddingOverride", context.getResources().getBoolean(R.bool.config_desktop_label_padding_override));
		return newD;
	}
	public static int getDesktopLabelPaddingRadius(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelPaddingRadius", context.getResources().getInteger(R.integer.config_desktop_label_padding_radius));
		return value;
	}
	public static int getDesktopLabelPaddingH(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelPaddingH", context.getResources().getInteger(R.integer.config_desktop_label_padding_h));
		return value;
	}
	public static int getDesktopLabelPaddingV(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelPaddingV", context.getResources().getInteger(R.integer.config_desktop_label_padding_v));
		return value;
	}
	public static boolean getDesktopLabelColorOverride(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("desktopLabelColorOverride", context.getResources().getBoolean(R.bool.config_desktop_label_color_override));
		return newD;
	}
	public static int getDesktopLabelColorText(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelColorText", context.getResources().getInteger(R.integer.config_desktop_label_color_text));
		return value;
	}
	public static int getDesktopLabelColorShadow(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelColorShadow", context.getResources().getInteger(R.integer.config_desktop_label_color_shadow));
		return value;
	}
	public static int getDesktopLabelColorBg(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int value = sp.getInt("desktopLabelColorBg", context.getResources().getInteger(R.integer.config_desktop_label_color_bg));
		return value;
	}

	public static boolean getAutosizeIcons(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("autosizeIcons", context.getResources().getBoolean(R.bool.config_autosize_icons));
		return newD;
	}
	public static boolean getDrawerZoom(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerZoom", context.getResources().getBoolean(R.bool.config_drawer_zoom));
		return newD;
	}
	public static boolean getDrawerLabels(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerLabels", context.getResources().getBoolean(R.bool.config_drawer_labels));
		return newD;
	}
	public static int getDrawerLabelSize(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int size = sp.getInt("drawerLabelTextSize", context.getResources().getInteger(R.integer.config_drawer_label_size)) + 8;
		return size;
	}
	public static boolean getDrawerLabelBold(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerLabelBold", context.getResources().getBoolean(R.bool.config_drawer_label_bold));
		return newD;
	}
	public static int getDrawerLabelColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("drawerLabelColor", context.getResources().getInteger(R.integer.config_drawer_label_color));
		return newD;
	}
	public static boolean getDrawerLabelColorOverride(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerLabelColorOverride", context.getResources().getBoolean(R.bool.config_drawer_label_color_override));
		return newD;
	}
	public static int getScreenCache(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("screenCache", context.getResources().getString(R.string.config_screen_cache)));
		return newD;
	}
	public static boolean getDesktopIndicator(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiDesktopIndicator", context.getResources().getBoolean(R.bool.config_desktop_indicator));
		return newD;
	}
	public static boolean getDesktopIndicatorAutohide(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiDesktopIndicatorAutohide", context.getResources().getBoolean(R.bool.config_desktop_indicator_autohide));
		return newD;
	}
	public static int getDesktopIndicatorType(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("uiDesktopIndicatorType", context.getResources().getString(R.string.config_desktop_indicator_type)));
		return newD;
	}
	public static boolean getDesktopIndicatorColorAllow(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiDesktopIndicatorColorAllow", context.getResources().getBoolean(R.bool.config_desktop_indicator_color_allow));
		return newD;
	}
	public static int getDesktopIndicatorColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("uiDesktopIndicatorColor", context.getResources().getInteger(R.integer.config_desktop_indicator_color));
		return newD;
	}
	public static String getSwipeDownAppToLaunchPackageName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("swipeDownAppToLaunchPackageName", "");
	}
	public static String getSwipeUpAppToLaunchPackageName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("swipeUpAppToLaunchPackageName", "");
	}
	public static String getPinchInAppToLaunchPackageName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("pinchInAppToLaunchPackageName", "");
	}
	public static String getHomeBindingAppToLaunchPackageName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("homeBindingAppToLaunchPackageName", "");
	}
	public static String getSwipeDownAppToLaunchName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("swipeDownAppToLaunchName", "");
	}
	public static String getSwipeUpAppToLaunchName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("swipeUpAppToLaunchName", "");
	}
	public static String getPinchInAppToLaunchName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("pinchInAppToLaunchName", "");
	}
	public static String getHomeBindingAppToLaunchName(Context context)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("homeBindingAppToLaunchName", "");
	}
	public static void setSwipeDownAppToLaunch(Context context, ApplicationInfo info)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("swipeDownAppToLaunchPackageName", info.intent.getComponent().getPackageName());
		editor.putString("swipeDownAppToLaunchName", info.intent.getComponent().getClassName());
		editor.commit();
	}
	public static void setSwipeUpAppToLaunch(Context context, ApplicationInfo info)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("swipeUpAppToLaunchPackageName", info.intent.getComponent().getPackageName());
		editor.putString("swipeUpAppToLaunchName", info.intent.getComponent().getClassName());
		editor.commit();
	}
	public static void setPinchInAppToLaunch(Context context, ApplicationInfo info)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("pinchInAppToLaunchPackageName", info.intent.getComponent().getPackageName());
		editor.putString("pinchInAppToLaunchName", info.intent.getComponent().getClassName());
		editor.commit();
	}
	public static void setHomeBindingAppToLaunch(Context context, ApplicationInfo info)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("homeBindingAppToLaunchPackageName", info.intent.getComponent().getPackageName());
		editor.putString("homeBindingAppToLaunchName", info.intent.getComponent().getClassName());
		editor.commit();
	}
	public static int getSwipeDownActions(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("swipedownActions", context.getResources().getString(R.string.config_swipedown_actions)));
		return newD;
	}
	public static int getSwipeUpActions(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("swipeupActions", context.getResources().getString(R.string.config_swipeup_actions)));
		return newD;
	}
	public static int getPinchInActions(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("pinchinActions", context.getResources().getString(R.string.config_pinchin_actions)));
		return newD;
	}
	public static String getThemePackageName(Context context, String default_theme)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return sp.getString("themePackageName", default_theme);
	}
	public static void setThemePackageName(Context context, String packageName)
	{
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("themePackageName", packageName);
		editor.commit();
	}
	public static boolean getThemeIcons(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("themeIcons", true);
		return newD;
	}
	
	public static int getRegistration(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("okmmId", 0);
		return newD;
	}
	
	public static void setRegistration(Context context, int okmmId) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("okmmId", okmmId);
		editor.commit();
	}
	
	public static int getDesktopOrientation(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("homeOrientation", context.getResources().getString(R.string.config_orientation_default)));
		return newD;
	}
	public static boolean getWallpaperScrolling(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("wallpaper_scrolling", context.getResources().getBoolean(R.bool.config_wallpaper_scroll));
		return newD;
	}
	public static void setDesktopScreens(Context context,int screens) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("desktopScreens", screens-1);
		editor.commit();
	}
	public static void setDefaultScreen(Context context,int screens) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("defaultScreen", screens);
		editor.commit();
	}

	public static int getCurrentAppCatalog(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("currentAppCatalog", -1);
		return newD;
	}
	public static void setCurrentAppCatalog(Context context, int group) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("currentAppCatalog", group);
		editor.commit();
	}

	public static void setChangelogVersion(Context context,String version) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("changelogReadVersion", version);
		editor.commit();
	}
	public static boolean shouldShowChangelog(Context context) {
		Boolean config=context.getResources().getBoolean(R.bool.config_nag_screen);
		if(config){
			SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
			String readV = sp.getString("changelogReadVersion", "0");
			String actualV=context.getString(R.string.app_version);
			boolean ret=!readV.equals(actualV);
			if(ret){
				//Once verified and showed, disable it ultill the next update
				setChangelogVersion(context, actualV);
			}
			return ret;
		}else{
			return false;
		}
	}
	/**
	 * Creates the "changes" dialog to be shown when updating ADW.
	 * @author adw
	 *
	 */
	public static class RegistrationDialogBuilder {
	  public static void create(final Context ctx) throws NameNotFoundException {
		final View registrationView = LayoutInflater.from(ctx).inflate(R.layout.registration, null);
	    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ctx)
		            .setTitle(ctx.getResources().getString(R.string.registration))
		            .setCancelable(false)
			        .setPositiveButton(ctx.getString(android.R.string.ok), null);
		final AlertDialog dialog = alertBuilder.setView(registrationView).create();
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){            
	       @Override
	       public void onClick(View v){
	         RequestParams params = new RequestParams();
	         EditText etName = (EditText)registrationView.findViewById(R.id.etName);
	   	     EditText etLastName = (EditText)registrationView.findViewById(R.id.etLastName);
	   	     EditText etAge = (EditText)registrationView.findViewById(R.id.etAge);
	   	     EditText etStreet = (EditText)registrationView.findViewById(R.id.etStreet);
	   	     EditText etNeighborhood = (EditText)registrationView.findViewById(R.id.etNeighborhood);
	   	     EditText etZipCode = (EditText)registrationView.findViewById(R.id.etZipCode);
	   	     RadioGroup rdgGenre = (RadioGroup)registrationView.findViewById(R.id.rdgGenre);
	   	     TelephonyManager telemamanger = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
             int errMessageId = 0;
             if((errMessageId = validateRegistration(registrationView))== 0){
               params.put("nombre", etName.getText().toString());
               params.put("apellidos", etLastName.getText().toString());
               params.put("edad", etAge.getText().toString());        		   
               params.put("genero", rdgGenre.getCheckedRadioButtonId() == R.id.rbMale ? 1 : 2);
               params.put("calle", etStreet.getText().toString());
               params.put("colonia", etNeighborhood.getText().toString());
               params.put("cp", etZipCode.getText().toString());
               params.put("no_sim ",  telemamanger.getSimSerialNumber());
               params.put("imei ", telemamanger.getDeviceId());
               ToastBuilder.show(params.toString(), ctx);
               RestClient.post("registro", params, new RestResponseHandler(ctx) {
             	  @Override
             	  public void onSuccess(JSONObject response) throws JSONException {
             		  dialog.dismiss(); 
             	  }    
             	});
             } else {
             	ToastBuilder.show(errMessageId, ctx);
             }
	       }
	     });
	  }
	}
	
	private static int validateRegistration(View registrationView){
	  int errMessageId = 0;
	  EditText etName = (EditText)registrationView.findViewById(R.id.etName);
	  EditText etLastName = (EditText)registrationView.findViewById(R.id.etLastName);
	  EditText etAge = (EditText)registrationView.findViewById(R.id.etAge);
	  EditText etStreet = (EditText)registrationView.findViewById(R.id.etStreet);
	  EditText etNeighborhood = (EditText)registrationView.findViewById(R.id.etNeighborhood);
	  EditText etZipCode = (EditText)registrationView.findViewById(R.id.etZipCode);
	  RadioGroup rdgGenre = (RadioGroup)registrationView.findViewById(R.id.rdgGenre);
	  if(etName.getText().toString().isEmpty()){
		  errMessageId = R.string.errNameRequired;
	  } else if (etLastName.getText().toString().isEmpty()){
		  errMessageId = R.string.errLastNameRequired;
	  } else if (etStreet.getText().toString().isEmpty()){
		  errMessageId = R.string.errStreetRequired;
	  } else if (etNeighborhood.getText().toString().isEmpty()){
		  errMessageId = R.string.errNeighborhoodRequired;
	  } else if (etZipCode.getText().toString().isEmpty()){
		  errMessageId = R.string.errZipCodeRequired;
	  } else if (etAge.getText().toString().isEmpty()){
		  errMessageId = R.string.errAgeRequired;
	  } else if (rdgGenre.getCheckedRadioButtonId() == -1){
		  errMessageId = R.string.errGenreRequired;
	  }
	  return errMessageId;
	}

	public static boolean getDebugShowMemUsage(Context context) {
		if(MyLauncherSettings.IsDebugVersion){
			SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
			boolean newD = sp.getBoolean("dbg_show_mem", false);
			return newD;
		}else{
			return false;
		}
	}
	public static boolean getDrawerCatalogsNavigation(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerCatalogNavigate", context.getResources().getBoolean(R.bool.config_drawer_navigate_catalogs));
		return newD;
	}
	public static boolean getDrawerCatalogsFlingNavigation(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerCatalogFlingNavigate", context.getResources().getBoolean(R.bool.config_drawer_catalog_fling_navigate));
		return newD;
	}
	public static boolean getDrawerUngroupCatalog(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerCatalogUngroup", context.getResources().getBoolean(R.bool.config_drawer_ungroup_catalog));
		return newD;
	}
	public static boolean getDrawerTitleCatalogs(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("drawerCatalogShow", context.getResources().getBoolean(R.bool.config_drawer_title_catalogs));
		return newD;
	}
	public static boolean getNotifReceiver(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("notifReceiver", context.getResources().getBoolean(R.bool.config_notif_receiver));
		return newD;
	}
	public static int getNotifSize(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int def_screen = sp.getInt("notifSize", context.getResources().getInteger(R.integer.config_notif_text_size))+10;
		return def_screen;
	}
	public static int getDockStyle(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("main_dock_style", context.getResources().getString(R.string.config_main_dock_style)));
		return newD;
	}
	public static boolean getDockLockMAB(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("mainDockLockMAB", true);
		return newD;
	}
	public static boolean getDockHide(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("mainDockDrawerHide", context.getResources().getBoolean(R.bool.config_main_dock_drawer_hide));
		return newD;
	}

	public static int getDrawerStyle(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("drawerStyle", context.getResources().getString(R.string.config_drawer_style)));
		return newD;
	}
	public static int getDeletezoneStyle(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = Integer.valueOf(sp.getString("deleteZoneLocation", context.getResources().getString(R.string.config_deletezone_style)));
		return newD;
	}
	public static boolean getUIABTint(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("uiABTint", context.getResources().getBoolean(R.bool.config_ab_tint));
		return newD;
	}
	public static int getUIABTintColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("uiABTintColor", context.getResources().getInteger(R.integer.config_ab_tint_color));
		return newD;
	}
	public static int getUIABSelectorColor(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		int newD = sp.getInt("uiABSelectorColor", context.getResources().getInteger(R.integer.config_ab_selector_color));
		return newD;
	}
	public static boolean getLauncherLocked(Context context) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		boolean newD = sp.getBoolean("launcherLocked", false);
		return newD;
	}
	public static void setLauncherLocked(Context context, boolean lock) {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putBoolean("launcherLocked", lock);
		editor.commit();
	}
	public static int getDesktopTransitionStyle(Context context)    {
		SharedPreferences sp = context.getSharedPreferences(LEGACY_PREFERENCES, Context.MODE_PRIVATE);
		return Integer.valueOf( sp.getString("desktopTransitionStyle", context.getResources().getString(R.string.config_desktop_transition)));
	}
}
