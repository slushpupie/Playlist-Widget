package com.slushpupie.playlist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Track implements Comparable<Track> {

	private String title;
	private String creator;
	private String album;
	private String annotation;
	private String duration;
	private String info;
	private String songId;
	private Date playdate;
	
	private static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void clear() {
		title = null;
		creator = null;
		album = null;
		annotation = null;
		duration = null;
		info = null;
		songId = null;
		playdate = null;
	}
	
	public Track copy() {
		Track copy = new Track();
		copy.setTitle(title);
		copy.setCreator(creator);
		copy.setAlbum(album);
		copy.setAnnotation(annotation);
		copy.setDuration(duration);
		copy.setInfo(info);
		copy.setSongId(songId);
		if(playdate != null) 
			copy.playdate = (Date) playdate.clone();
		else
			copy.playdate = null;
		
		return copy;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbum() {
		return album;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDuration() {
		return duration;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setSongId(String songId) {
		this.songId = songId;
	}

	public String getSongId() {
		return songId;
	}

	public void setPlaydate(String playdate) {
		try {
			this.playdate = FORMATTER.parse(playdate.trim());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public Date getPlaydate() {
		return playdate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Track other = (Track) obj;
		
		if(title == null) {
			if(other.title != null)
				return false;
		} else if(!title.equals(other.title)){
			return false;
		}
		if(creator == null) {
			if(other.creator != null)
				return false;
		} else if(!creator.equals(other.creator)){
			return false;
		}
		if(album == null) {
			if(other.album != null)
				return false;
		} else if(!album.equals(other.album)){
			return false;
		}
		if(annotation == null) {
			if(other.annotation != null)
				return false;
		} else if(!annotation.equals(other.annotation)){
			return false;
		}
		if(duration == null) {
			if(other.duration != null)
				return false;
		} else if(!duration.equals(other.duration)){
			return false;
		}
		if(info == null) {
			if(other.info != null)
				return false;
		} else if(!info.equals(other.info)){
			return false;
		}
		if(songId == null) {
			if(other.songId != null)
				return false;
		} else if(!songId.equals(other.songId)){
			return false;
		}
		if(playdate == null) {
			if(other.playdate != null)
				return false;
		} else if(!playdate.equals(other.playdate)){
			return false;
		}
	
		return true;
	}
	
	public int compareTo(Track another) {
		if (another == null) return 1;
		return another.playdate.compareTo(playdate);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(title != null) sb.append("title: ").append(title).append("\n");
		if(creator != null) sb.append("creator: ").append(creator).append("\n");
		if(album != null) sb.append("album: ").append(album).append("\n");
		if(annotation != null) sb.append("annotation: ").append(annotation).append("\n");
		if(duration != null) sb.append("duration: ").append(duration).append("\n");
		if(info != null) sb.append("info: ").append(info).append("\n");
		if(songId != null) sb.append("songId: ").append(songId).append("\n");
		if(playdate != null) sb.append("playdate: ").append(FORMATTER.format(playdate)).append("\n");
		
		return sb.toString();
	}
	
}
