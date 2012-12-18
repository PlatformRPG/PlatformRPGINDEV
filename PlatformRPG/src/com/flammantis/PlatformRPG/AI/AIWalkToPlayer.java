package com.flammantis.PlatformRPG.AI;

import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.models.Enemy;

public class AIWalkToPlayer extends AI
{

	public int detectRange;
	public boolean onPlayer = false;
	int timeonplayer = 0;
	boolean canbeonplayer = true;
	boolean alreadyNotified = false;

	public AIWalkToPlayer(Enemy enemy, int detectRange, World world)
	{
		super(enemy, world);
		// TODO Auto-generated constructor stub
		this.detectRange = detectRange;
	}

	@Override
	public void execute()
	{
		
		if (world.enemiesOnPlayer >= 3 && !this.onPlayer)
			return;
		
		if ((world.thePlayer.position.x - enemy.position.x) > this.detectRange && !this.onPlayer)
			return;
		
		this.walkToPlayer();
		
		

	}
	

	
	private void walkToPlayer()
	{
		
		if (!canbeonplayer)
			return;
		
		int direction = 0;
		this.onPlayer = true;
		
		this.timeonplayer++;
		
		if (timeonplayer > 1000)
		{
			
			this.canbeonplayer = false;
			this.world.enemiesOnPlayer--;
			this.onPlayer = false;
			this.enemy.position.x = this.enemy.origX;
			
		}
		
		
		
		if ((int) this.world.thePlayer.position.x == (int) this.enemy.position.x)
			return;
		
		if (this.world.thePlayer.position.x < this.enemy.position.x)
			direction = -1;
		else
			direction = 1;
		
		if ((int)this.world.thePlayer.position.y > (int) this.enemy.position.y && enemy.onGround)
			this.enemy.jump();
		
		if (direction > 0)
			this.enemy.velocity.x = Enemy.WALK_VELOCITY;
		else
			this.enemy.velocity.x = -Enemy.WALK_VELOCITY;
		
		if (!alreadyNotified)
		{
			
			world.enemiesOnPlayer++;
			this.alreadyNotified = true;
			
		}
		
	}

}
