package com.slushpupie.playlist;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public abstract class BasePlaylistParser implements PlaylistParser {

	private final URL playlistURL;
	
	protected BasePlaylistParser(String playlistURL) {
		try {
			this.playlistURL = new URL(playlistURL);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected InputStream getInputStream() {
		try {
			return playlistURL.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
