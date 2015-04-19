package com.my.badgeappbar;


import com.example.badgeappbar.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ActivityMain extends Activity{
	
	MaterialAppBar appbar;
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		
		//hide the actionbar if you use action bar activity
		this.getActionBar().hide();
		
		
		setContentView(R.layout.activity_main);
		
		appbar = (MaterialAppBar)findViewById(R.id.appbar);
		appbar.addButtonToLeft("navigation", R.drawable.ic_menu_white_24dp);
		appbar.addButtonToRight("notification", R.drawable.ic_notifications_none_white_24dp);
		appbar.pushBadgeByName("notification", Color.RED);
		appbar.setAppbarBackgroundColor(getResources().getColor(R.color.donnaPurpleNormal));
		
		
		appbar.addOverflowButton(new String[]{"Settings", "Daniel Shum"}, new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				
				if (arg0.getTitle().equals("Settings")){
					Toast.makeText(ActivityMain.this, "Settings tap", Toast.LENGTH_LONG).show();
				}else if (arg0.getTitle().equals("Daniel Shum")){
					Toast.makeText(ActivityMain.this, "Daniel Shum tap", Toast.LENGTH_LONG).show();
				}
				
				return false;
			}
			
		}, R.drawable.ic_more_vert_white_24dp);
		
		appbar.setOnButtonClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.equals(appbar.getButtonByName("navigation"))){
					Toast.makeText(ActivityMain.this, "Navigation", Toast.LENGTH_LONG).show();
				}else if(v.equals(appbar.getButtonByName("notification"))){
					Toast.makeText(ActivityMain.this, "Notification", Toast.LENGTH_LONG).show();
				}
			}
			
		});

	}

}
