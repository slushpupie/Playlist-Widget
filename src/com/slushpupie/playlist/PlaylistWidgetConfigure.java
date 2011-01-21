package com.slushpupie.playlist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;


public class PlaylistWidgetConfigure extends Activity {

	private GoogleAnalyticsTracker tracker;
	public static String GA_ID = PlaylistWidgetProvider.GA_ID;
	
	private SeekBar configSeekBar;
	private TextView minutesDisplay;
	private Button configOkButton;
	
	
	private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		tracker = GoogleAnalyticsTracker.getInstance();

		// Start the tracker in manual dispatch mode...
		tracker.start(GA_ID, this);
		tracker.trackPageView("/configure");
		
		setResult(RESULT_CANCELED);
		setContentView(R.layout.config);
		
		minutesDisplay = (TextView)findViewById(R.id.mindisplayconfig);
		
		configSeekBar = (SeekBar)findViewById(R.id.seekbarconfig);
		configSeekBar.setMax(120);
		configSeekBar.setProgress(0);
		configSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(progress == 0) {
					minutesDisplay.setText("never");
				} else {
					minutesDisplay.setText(progress+" min");
				}
			}
			public void onStartTrackingTouch(SeekBar seekBar){}
			public void onStopTrackingTouch(SeekBar seekBar){}
		});
		
		configOkButton = (Button)findViewById(R.id.okconfig);
		configOkButton.setOnClickListener( new Button.OnClickListener() {
			public void onClick(View view) {
				final Context context = PlaylistWidgetConfigure.this;
				AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
				UpdateService.setUpdateInterval(configSeekBar.getProgress()*60*1000);
				
				tracker.trackEvent(
					"Clicks",  // Category
					"Button",  // Action
					"SetRefreshTime", // Label
					(configSeekBar.getProgress()*60*1000)); // Value
				
				PlaylistWidgetProvider.doBuildUpdate(context, appWidgetId);
				
				Intent intent = new Intent();
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				setResult(RESULT_OK, intent);
				finish();
				
			}
		});
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, 
				                    AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if(appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tracker.stop();
	}
}
