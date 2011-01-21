package com.slushpupie.playlist;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;


public class UpdateService extends IntentService {

	private GoogleAnalyticsTracker tracker;
	public static String GA_ID = PlaylistWidgetProvider.GA_ID;

	private static long update_interval = 0;
	private PendingIntent pendingUpdate;
	
	public static final String REFRESH_ACTION = "com.slushpupie.playlist.REFRESH";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("h:mm");

	public UpdateService() {
		super("UpdateService");
	}

	public static void setUpdateInterval(long interval) {
		Log.d(PlaylistWidgetProvider.LOG_TAG,"Setting update interval to "+(interval/60000)+"min");
		update_interval = interval;
	}
	
	protected void onHandleIntent(Intent intent) {
		RemoteViews updateViews = buildUpdate(this);
		
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.start(GA_ID, this);
		tracker.trackPageView("/playlist");
		tracker.dispatch();
		tracker.stop();
		ComponentName thisWidget = new ComponentName(this, PlaylistWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(thisWidget, updateViews);
	}

	public RemoteViews buildUpdate(Context context) {
		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.playlist);
		//TODO: Replace this URL with your song data pages here, %s is replaced with the song id
		String songdataURI = "http://example.com/playlist/data.php?id=%s";
		List<Track> playlist = null;
		String error = "";
		
		try {
			//TODO: Replace this URL with the xml playlist URL for your music service
			SaxPlaylistParser parser = new SaxPlaylistParser("http://example.com/playlist/playlist.xml");
			playlist = parser.parse();
		} catch (Exception e) {
			Log.e(PlaylistWidgetProvider.LOG_TAG,"Couldn't parse playlist", e);
			playlist = null;
			error = "Playlist error: "+e.getMessage();
		}
		
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.start(GA_ID, this);

		
		if(playlist != null || playlist.size() > 0) {
			Track track;
			Intent intent;
			PendingIntent pi;
			
			
			
			intent = new Intent(context, PlaylistWidgetProvider.class);
			intent.setAction(REFRESH_ACTION);
			pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			updateViews.setOnClickPendingIntent(R.id.logo, pi);

			if(playlist.size() > 0) {				
				track = playlist.get(0);
				updateViews.setTextViewText(R.id.track_title0, track.getTitle());
				updateViews.setTextViewText(R.id.track_artist0, track.getCreator());
				updateViews.setTextViewText(R.id.played_at0, DATE_FORMAT.format(track.getPlaydate()));
				if(track.getSongId() != null) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(songdataURI,track.getSongId())));
					pi = PendingIntent.getActivity(context, 0, intent, 0);
					updateViews.setOnClickPendingIntent(R.id.track0, pi);
					updateViews.setOnClickPendingIntent(R.id.track_title0, pi);
					updateViews.setOnClickPendingIntent(R.id.track_artist0, pi);
	
	
				}
			}
			
			if(playlist.size() > 1) {
				track = playlist.get(1);
				updateViews.setTextViewText(R.id.track_title1, track.getTitle());
				updateViews.setTextViewText(R.id.track_artist1, track.getCreator());
				updateViews.setTextViewText(R.id.played_at1, DATE_FORMAT.format(track.getPlaydate()));
				if(track.getSongId() != null) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(songdataURI,track.getSongId())));
					pi = PendingIntent.getActivity(context, 0, intent, 0);
					updateViews.setOnClickPendingIntent(R.id.track_title1, pi);
					updateViews.setOnClickPendingIntent(R.id.track_artist1, pi);
				}
			}
			
			if(playlist.size() > 2) {
				track = playlist.get(2);
				updateViews.setTextViewText(R.id.track_title2, track.getTitle());
				updateViews.setTextViewText(R.id.track_artist2, track.getCreator());
				updateViews.setTextViewText(R.id.played_at2, DATE_FORMAT.format(track.getPlaydate()));
				if(track.getSongId() != null) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(songdataURI,track.getSongId())));
					pi = PendingIntent.getActivity(context, 0, intent, 0);
					updateViews.setOnClickPendingIntent(R.id.track_title2, pi);
					updateViews.setOnClickPendingIntent(R.id.track_artist2, pi);
				}
			}
			
			if(playlist.size() > 3) {
				track = playlist.get(3);
				updateViews.setTextViewText(R.id.track_title3, track.getTitle());
				updateViews.setTextViewText(R.id.track_artist3, track.getCreator());
				updateViews.setTextViewText(R.id.played_at3, DATE_FORMAT.format(track.getPlaydate()));
				if(track.getSongId() != null) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(songdataURI,track.getSongId())));
					pi = PendingIntent.getActivity(context, 0, intent, 0);
					updateViews.setOnClickPendingIntent(R.id.track_title3, pi);
					updateViews.setOnClickPendingIntent(R.id.track_artist3, pi);
				}
			}
			
			if(playlist.size() > 4) {
				track = playlist.get(4);
				updateViews.setTextViewText(R.id.track_title4, track.getTitle());
				updateViews.setTextViewText(R.id.track_artist4, track.getCreator());
				updateViews.setTextViewText(R.id.played_at4, DATE_FORMAT.format(track.getPlaydate()));
				if(track.getSongId() != null) {
					intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(songdataURI,track.getSongId())));
					pi = PendingIntent.getActivity(context, 0, intent, 0);
					updateViews.setOnClickPendingIntent(R.id.track_title4, pi);
					updateViews.setOnClickPendingIntent(R.id.track_artist4, pi);
				}
			}	
			tracker.trackPageView("/playlist");

		} else {
			updateViews = new RemoteViews(context.getPackageName(), R.layout.playlist);
			updateViews.setTextViewText(R.id.track_title0, "Unable to load playlist");
			updateViews.setTextViewText(R.id.track_artist0, error);
			updateViews.setTextViewText(R.id.played_at0, "");
			Intent intent = new Intent(context, PlaylistWidgetProvider.class);
			intent.setAction(REFRESH_ACTION);
			PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			updateViews.setOnClickPendingIntent(R.id.playlist_widget, pi);
			
			updateViews.setTextViewText(R.id.track_title1, "");
			updateViews.setTextViewText(R.id.track_artist1, "");
			updateViews.setTextViewText(R.id.played_at1, "");
			
			updateViews.setTextViewText(R.id.track_title2, "");
			updateViews.setTextViewText(R.id.track_artist2, "");
			updateViews.setTextViewText(R.id.played_at2, "");
			
			updateViews.setTextViewText(R.id.track_title3, "");
			updateViews.setTextViewText(R.id.track_artist3, "");
			updateViews.setTextViewText(R.id.played_at3, "");
			
			updateViews.setTextViewText(R.id.track_title4, "");
			updateViews.setTextViewText(R.id.track_artist4, "");
			updateViews.setTextViewText(R.id.played_at4, "");

			tracker.setCustomVar(
				1, //index
				"error message", //name
				error, //value
				3); //scope
			tracker.trackPageView("/error");			
		}
		
		scheduleUpdate(context, update_interval);
		tracker.stop();
		return updateViews;
		
	}
	
	public void scheduleUpdate(Context context, long interval) {
		
		if(interval > 0) {
			Intent intent = new Intent(context, PlaylistWidgetProvider.class);
			intent.setAction(REFRESH_ACTION);
			if(pendingUpdate == null) {
				pendingUpdate = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			}
			
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			am.cancel(pendingUpdate);
			am.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+interval, pendingUpdate);
			
			Log.d(PlaylistWidgetProvider.LOG_TAG,"Set update alarm for "+(interval/60000)+"min from now");
		} 
	
	}
		
}
