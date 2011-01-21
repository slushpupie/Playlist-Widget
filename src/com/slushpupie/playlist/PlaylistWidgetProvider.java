package com.slushpupie.playlist;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;


public class PlaylistWidgetProvider extends AppWidgetProvider {

	public static final String LOG_TAG = "PlaylistWidget";
	public static final String REFRESH_ACTION = "com.slushpupie.playlist.REFRESH";
	//TODO: Replace this with your Google Analytics tracking ID
	public static final String GA_ID = "UA-00000000-0";
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("h:mm");	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// perform updates in a service
		context.startService(new Intent(context, UpdateService.class));
	}
	
	public static void doBuildUpdate(Context context, int widgetId) {
		Log.d(LOG_TAG,"Starting update...");
		context.startService(new Intent(context, UpdateService.class));
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		try{
			String action = intent.getAction();
			int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			Log.d(LOG_TAG,"Got action: "+intent.getAction());
			if(action.equals(REFRESH_ACTION)) {
				doBuildUpdate(context, widgetId);
			} else if(action.equals(AppWidgetManager.ACTION_APPWIDGET_DELETED)) {
				//Workaround for 1.5 bug
				final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 
				                                           AppWidgetManager.INVALID_APPWIDGET_ID);
				if(appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
					this.onDeleted(context, new int[] {appWidgetId});
				}
			} else {
				Log.d("PlaylistWidgetProvider","Got an unknown intent: "+action);
				super.onReceive(context, intent);
			}
		} catch (Throwable t) {
			Log.d("PlaylistWidgetProvider","Error in onReceive()");	
		}
	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// last widget deleted?
		ComponentName thisWidget = new ComponentName(context, PlaylistWidgetProvider.class);
		int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds( thisWidget );
		if(ids.length == 0) {
			UpdateService.setUpdateInterval(0);
		}
		
		GoogleAnalyticsTracker tracker = GoogleAnalyticsTracker.getInstance();

		// Start the tracker in manual dispatch mode...
		tracker.start(GA_ID, context);
		tracker.trackEvent(
			"Clicks",  // Category
			"Widget",  // Action
			"Delete",  // Label
			0);        // Value
		tracker.stop();
	}
	
}
