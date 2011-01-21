package com.slushpupie.playlist;

import java.util.ArrayList;
import java.util.List;


import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.text.Html;
import android.util.Log;
import android.util.Xml;

public class SaxPlaylistParser extends BasePlaylistParser {

	private static String NAMESPACE = "http://xspf.org/ns/0/";
	
	public SaxPlaylistParser(String playlistURL) {
		super(playlistURL);
	}
	
	public List<Track> parse() {
		final Track currentTrack = new Track();
		final List<Track> tracks = new ArrayList<Track>();
		RootElement root = new RootElement(NAMESPACE, "playlist");
		Element trackList = root.getChild(NAMESPACE, "trackList");
		Element track = trackList.getChild(NAMESPACE, "track");

		
		track.setEndElementListener(new EndElementListener() {
			public void end() {
				tracks.add(currentTrack.copy());
				currentTrack.clear();
			}
		});
		track.getChild(NAMESPACE, "title").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setTitle(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "creator").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setCreator(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "album").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setAlbum(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "annotation").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setAnnotation(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "duration").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setDuration(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "info").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setInfo(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "song-id").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setSongId(Html.fromHtml(body).toString());
			}
		});
		track.getChild(NAMESPACE, "play-datetime").setEndTextElementListener(new EndTextElementListener(){
			public void end(String body) {
				currentTrack.setPlaydate(Html.fromHtml(body).toString());
			}
		});
	
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return tracks;
	}

}
