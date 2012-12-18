package com.flammantis.PlatformRPG;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flammantis.PlatformRPG.controllers.Assets;
import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.controllers.World.WorldListener;
import com.flammantis.PlatformRPG.models.WorldRenderer;
import com.flammantis.PlatformRPG.views.MainMenuScreen;

public class PlatformRPG extends Game
{

	SpriteBatch batch;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	public static PlatformRPG instance;
	float accel;
	
	@Override
	public void create()
	{
		Assets.loadPics();
		instance = this;
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose()
	{
		Assets.dispose();
	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{	
	}

}
