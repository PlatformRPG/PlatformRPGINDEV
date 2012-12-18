package com.flammantis.PlatformRPG;

import com.badlogic.gdx.ApplicationListener;

public class PlatformRPGAndroid implements ApplicationListener
{

	public PlatformRPG theGame;
	
	
	@Override
	public void create()
	{
		
		theGame = new PlatformRPG();
		theGame.create();

	}

	@Override
	public void resize(int width, int height)
	{
		theGame.resize(width, height);

	}

	@Override
	public void render()
	{
		
		theGame.render();

	}

	@Override
	public void pause()
	{
		
		theGame.pause();

	}

	@Override
	public void resume()
	{
	
		theGame.resume();

	}

	@Override
	public void dispose()
	{
	
		theGame.dispose();

	}

}
