package de.cake.musicController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class Queue {
	private List<AudioTrack> queuelist;
	private List<String> tracklist;
	private MusicController controller;
	
	public Queue(MusicController controller) {
		this.setController(controller);
		this.setQueuelist(new ArrayList<AudioTrack>());
		this.setTracklist(new ArrayList<String>());
	}
	
	public boolean next() {
		if(this.queuelist.size() >= 1) {
			AudioTrack track = queuelist.remove(0);
			//this.tracklist.remove(0);
			
			if(track != null) {
				this.controller.getPlayer().playTrack(track);
				return true;
			}
			
		}
		
		return false;
	}
	
	public boolean clear() {
		if(this.queuelist.size() >= 1) {
			this.queuelist.clear();
			//this.tracklist.clear();
		}
		
		//if(this.tracklist.size() >1) {
			//this.queuelist.clear();
			//this.tracklist.clear();
		//}
		
		return false;
	}
	
	public void removeTrack(Integer ammount) {
		
		for(int i = 0; i < ammount; i++) {
			this.queuelist.remove(0);
			//this.tracklist.remove(0);
		}
		
	}
	
	public void addTrackToQueue(AudioTrack track) {
		this.queuelist.add(track);
		
		if(controller.getPlayer().getPlayingTrack() == null) {
			next();
		}
	
	}
	
	public void addTrackToTopOfQueue(AudioTrack track, Integer pos) {
		this.queuelist.add(pos, track);
		
		if(controller.getPlayer().getPlayingTrack() == null) {
			next();
		}
		
	}
	
	public MusicController getController() {
		return controller;
	}
	
	public void setController(MusicController controller) {
		this.controller = controller;
	}
	
	public List<AudioTrack> getQueuelist() {
		return queuelist;
	}
	
	public void setQueuelist(List<AudioTrack> queuelist) {
		this.queuelist = queuelist;
	}
	
	public void setTracklist(List<String> tracklist) {
		this.tracklist = tracklist;
	}
	
	public void shuffel() {
		Collections.shuffle(queuelist);
		Collections.shuffle(tracklist);
	}
	
}
