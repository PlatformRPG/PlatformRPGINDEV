package com.flammantis.PlatformRPG.models;

import com.badlogic.gdx.math.Rectangle;
import com.flammantis.PlatformRPG.AI.AIWalkToPlayer;
import com.flammantis.PlatformRPG.controllers.World;

public class Goblin extends Enemy
{

	public static final float HEIGHT = 0.8f;
	public static final float WIDTH = 0.8f;
	
	
	public Goblin(float x, float y)
	{
		super(x, y, WIDTH, HEIGHT);
		this.boundingBox = new Rectangle(x - width / 4, y - height / 4, width, height);
		this.ais.add(new AIWalkToPlayer(this, 1, World.instance));
		this.hp = 50;
		
	}

	@Override
	public void hitPlayer(Player p)
	{
		
		p.damage(5);
		

	}
	
	@Override
	public void update(float delta)
	{
		
		super.update(delta);
				
	}

	

	

}
