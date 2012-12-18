package com.flammantis.PlatformRPG.AI;

import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.models.Enemy;

public abstract class AI
{

	public Enemy enemy;
	public World world;

	public AI(Enemy enemy, World world)
	{
		
		this.enemy = enemy;
		this.world = world;
		
	}
	
	public abstract void execute();
	
}
