package com.flammantis.PlatformRPG.models;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.flammantis.PlatformRPG.DynamicGameObject;
import com.flammantis.PlatformRPG.AI.AI;
import com.flammantis.PlatformRPG.AI.AIWalkToPlayer;
import com.flammantis.PlatformRPG.controllers.World;

public abstract class Enemy extends DynamicGameObject
{

	public static final int JUMP_VELOCITY = 15;
	public static final int WALK_VELOCITY = 5;
	
	public static final int WALKING = 0;
	public static final int IDLE = 1;
	public static final int JUMPING = 2;
	
	float width;
	float height;
	
	int hp;
	
	float moveX1;
	float moveX2;
	
	public int origX;
	

	public Array<AI> ais;

	
	int state;
	public int timeInState;
	public boolean colliding = false;
	
	public Enemy(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		this.width = width;
		this.height = height;
		this.moveX1 = x - new Random().nextInt(10);
		this.moveX2 = x + new Random().nextInt(10);
		state = 1;
		
		
		this.onGround = false;
		this.velocity.x = 2;
		
		this.origX = (int) x;
		
		this.ais = new Array<AI>();
		
		if (this.moveX2 < 2)
			this.moveX2 = 2;
		
	}
	
	
	public void jump()
	{
		
		this.velocity.y = Enemy.JUMP_VELOCITY;
		this.velocity.x = Enemy.WALK_VELOCITY;
		this.state = JUMPING;
		this.timeInState = 0;
		this.onGround = false;
		
	}
	
	public void update(float f)
	{
		
		boolean isonPlayer = false;
		
		for (AI ai : ais)
		{
			
			ai.execute();
			
			if (ai instanceof AIWalkToPlayer)
				isonPlayer = ((AIWalkToPlayer)ai).onPlayer;
			
		}
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		this.onGround = this.colliding;
		
		if (!onGround)
			velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		else
			velocity.add(World.gravity.x * deltaTime, 0);
		
			position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		boundingBox.x = position.x - width / 2;
		boundingBox.y = position.y - height / 2;

		if (this.velocity.x > 0 && this.state != WALKING)
		{	
			this.state = WALKING;
			this.timeInState = 0;
		}
		
		timeInState += 50;
		
		if(moveX1 > position.x || position.x > moveX2 && !isonPlayer)
		{	
			this.velocity.x = -this.velocity.x;			
		}
		
		if (position.x < 0)
			position.x = 0;
			
		if (position.x > World.WORLD_WIDTH)
			position.x = World.WORLD_WIDTH;
		
		if (this.position.y == 0)
		 {
			 this.onGround = true;
		 }
		 
		 if (this.position.y < 0)
		 {
			 
			 this.position.y = 0;
			 
		 }
		
		 if (isonPlayer)
		 {
		 if (this.position.y == 0 && (World.instance.thePlayer.position.y > this.position.y))
			this.jump();
		 
		 }
		 else
			 if (this.position.y == 0)
					this.jump(); 
		
	}
	
	public abstract void hitPlayer(Player p);
	
	public void hitPlatform()
	{
	
		this.onGround = true;
		this.velocity.y = 0;
		
	}


	public void takeDamage(int i)
	{
		
		this.hp -= i;
		
		 
		if (this.hp <= 0)
		{
			
			World.instance.goblins.remove(this);
			
		}
		
	}
	
	

}
