/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.okmm.android.launcher;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.okmm.android.launcher.widgets.DraggableListView;
import com.okmm.android.R;
import com.okmm.android.R.id;
import com.okmm.android.R.layout;
import com.okmm.android.R.string;

public class FolderIconReorderActivity extends ListActivity {

	protected final static String EXTRA_FOLDER_INFO_ID = "EXTRA_FOLDER_INFO_ID";

	private ListView mList;

	private boolean mDirty = false;

	private static final Collator sCollator = Collator.getInstance();

	UserFolderInfo mUserFolderInfo;

	private AlertDialog mAlertDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.order_folder_icon_activity);

		Bundle extras = getIntent().getExtras(); 

		if (extras != null) {
			final long id = extras.getLong(EXTRA_FOLDER_INFO_ID);
			final LauncherModel launcherModel = Launcher.getLauncherModel();
			// check for valid folder
			if (launcherModel==null || id==0) {
				finish();
				return;
			}

			mUserFolderInfo = (UserFolderInfo) launcherModel.findFolderById(id);
			this.setTitle(getString(R.string.activity_label_reorder_activity) + mUserFolderInfo.title);
		} else {
			finish();
			return;
		}

		mList = getListView();

		DraggableListView dragList = (DraggableListView) mList;
		dragList.setDropListener(mDropListener);

		setListAdapter(new FolderIconAdapter(this));

		Button button;
		button = (Button) findViewById(R.id.button_folder_icon_done);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		button = (Button) findViewById(R.id.button_folder_icon_sort);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAlertDialog = new AlertDialog.Builder(FolderIconReorderActivity.this)
				.setMessage(R.string.dialog_folder_icon_reorder_sort)
				.setPositiveButton(android.R.string.ok, 
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						sortFolderIcons();
					}
				})
				.setNegativeButton(android.R.string.cancel, null).create();
				mAlertDialog.show();
			}
		});
	}

	@Override
	public void onDestroy() {
		if (mAlertDialog != null) {
			if (mAlertDialog.isShowing()) {
				mAlertDialog.dismiss();
				mAlertDialog = null;
			}
		}

		if (mDirty) {
			Toast.makeText(this, getString(R.string.toast_folder_icon_reorder_finished) + mUserFolderInfo.title, Toast.LENGTH_SHORT).show();
			// Make the database adhere to the list
			for (ApplicationInfo item : mUserFolderInfo.contents) {
				LauncherModel.deleteItemFromDatabase(this, item);
				LauncherModel.addItemToDatabase(this, item, mUserFolderInfo.id, 0, 0, 0, false);
			}
		}

		DraggableListView dragList = (DraggableListView) mList;
		dragList.setDropListener(null);

		setListAdapter(null);

		super.onDestroy();
	}

	private DraggableListView.DropListener mDropListener = new DraggableListView.DropListener() {
		public void drop(int from, int to) {

			// move the icon
			if(from < mUserFolderInfo.contents.size()) {
				ApplicationInfo icon = mUserFolderInfo.contents.remove(from);
				if(to <= mUserFolderInfo.contents.size()) {
					mUserFolderInfo.contents.add(to, icon);
					mList.invalidateViews();
					mDirty = true;
				}
			}
		}
	};

	private class FolderIconAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public FolderIconAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mUserFolderInfo.contents.size();
		}

		public Object getItem(int position) {
			return mUserFolderInfo.contents.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final View v;
			if (convertView == null) {
				v = mInflater.inflate(R.layout.order_folder_icon_list_item, null);
			} else {
				v = convertView;
			}

			ApplicationInfo appInfo = mUserFolderInfo.contents.get(position);

			final TextView name = (TextView)v.findViewById(R.id.name);
			final ImageView icon = (ImageView)v.findViewById(R.id.icon);

			name.setText(appInfo.toString());

			icon.setImageDrawable(appInfo.icon);

			return v;
		}

		// these two functions disable list highlighting
		@Override
		public boolean areAllItemsEnabled() { 
			return false; 
		} 
		@Override
		public boolean isEnabled(int position) { 
			return false; 
		} 
	}

	private void sortFolderIcons() {
		// it is possible to sort the list if there is more than one icon in it
		if (mList.getChildCount() > 1) {
			Comparator<ApplicationInfo> revert = Collections.reverseOrder(new FolderIconNameComparator());
			Collections.sort(mUserFolderInfo.contents, revert);
			mList.invalidateViews();
			mDirty = true;
		}
	}

	private static class FolderIconNameComparator implements Comparator<ApplicationInfo> {
		public final int compare(ApplicationInfo a, ApplicationInfo b) {
			int result = sCollator.compare(b.title, a.title);
			return result;
		}
	}
}