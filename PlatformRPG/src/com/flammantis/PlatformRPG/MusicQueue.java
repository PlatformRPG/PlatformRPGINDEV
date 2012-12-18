package com.flammantis.PlatformRPG;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

public class MusicQueue
{

	private Array<Music> music;
	private int lastPlayedMusicIndex;
	private boolean playedATrack;

	public MusicQueue(Array<Music> music)
	{
		
		this.music = music;
		
	}
	
	public void update()
	{
		// if false, BAD
		if (music.size == 0)
			return;
		
		boolean playingAnything = false;
		for (Music m : music)		
		{
			
			if (m.isPlaying())
				playingAnything = true;
			
		}
		
		// If not playing anything, let's move to the next track / If we haven't played anything yet :) 
		if (!playingAnything)
		{
			
			// Should we loop to the start of the playlist?
			if ((lastPlayedMusicIndex + 1) > (music.size - 1) ||  !playedATrack)
			{
				// Play the first track
				this.lastPlayedMusicIndex = 0;
				this.music.get(0).play();
				this.playedATrack = true;
				
				
			}
			else
			{
				
				// Otherwise, we'll play the next track 
				this.lastPlayedMusicIndex++;
				this.music.get(lastPlayedMusicIndex).play();
				
			}
			
		}
		
	}
	
}
