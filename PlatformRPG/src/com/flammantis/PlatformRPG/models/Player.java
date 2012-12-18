package com.flammantis.PlatformRPG.models;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flammantis.PlatformRPG.DamageCalc;
import com.flammantis.PlatformRPG.DamageText;
import com.flammantis.PlatformRPG.DynamicGameObject;
import com.flammantis.PlatformRPG.PlatformRPG;
import com.flammantis.PlatformRPG.controllers.Assets;
import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.views.MainMenuScreen;

public class Player extends DynamicGameObject
{

	public static final int PLAYER_IDLE = 0;
	public static final int PLAYER_WALKING = 1;
	public static final int PLAYER_JUMPING = 2;
	public static final int PLAYER_FALLING = 3;
	public static final int MOVE_VELOCITY = 15;
	public static final int JUMP_VELOCITY = 13;
	public static final float WIDTH = 0.8f;
	public static final float HEIGHT = 0.8f;
	public static final int PLAYER_ATTACKING = 4;

	public int currentState = 0;
	float timeInState = 0;
	public int hp = 100;
	public int maxHP = 100;
	public float lastHurtTime = 0;
	public boolean hasBeenHit = false;
	public BitmapFont font = new BitmapFont();
	
	public Player(float x, float y)
	{
		super(x, y, WIDTH, HEIGHT);
		

	}

	public void update(float deltaTime)
	{
		if (!onGround && this.currentState != Player.PLAYER_JUMPING)
			velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		else
			velocity.add(World.gravity.x * deltaTime, 0);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		boundingBox.x = position.x - boundingBox.width / 2;
		boundingBox.y = position.y - boundingBox.height / 2;

		if (this.currentState == Player.PLAYER_ATTACKING && this.timeInState > 0.6f)
		{
			this.currentState = Player.PLAYER_IDLE;
			this.timeInState = 0;
		}
			
		
		
		if (velocity.y > 0)
		{
			if (currentState != PLAYER_JUMPING)
			{
				currentState = PLAYER_JUMPING;
				timeInState = 0;
			}
		}

		

		if (position.x < 0)
			position.x = 0;
			
		if (position.x > World.WORLD_WIDTH)
			position.x = World.WORLD_WIDTH;

		timeInState += deltaTime;
		if (this.hasBeenHit) lastHurtTime += deltaTime;
		
		
		if (hp <= 0)
		{
			
			PlatformRPG.instance.setScreen(new MainMenuScreen(PlatformRPG.instance));
			
		}
		
		if (this.currentState == Player.PLAYER_ATTACKING)	
			this.attackWithSword();
			
		
		
		
		
		
	}
	
	public void hitPlatform()
	{
		
		this.onGround = true;
		this.velocity.y = 0;
		
	}

	public void damage(int amount)
	{
	
		if (lastHurtTime >= 1.51f || !hasBeenHit)
		{
			
			this.hp -= amount;
			this.lastHurtTime = 0;
			Assets.hurtSound.play();
			hasBeenHit = true;
			
		}
		
	}

	public boolean canBeDamaged()
	{
		
		return lastHurtTime >= 1.51f;
		
	}

	public void attackWithSword()
	{

		ArrayList<Goblin> goblins = (ArrayList<Goblin>) World.instance.goblins.clone();
		
		for (Goblin g : goblins)
		{
			
			if ( g.position.x -  this.position.x  <= 1.5f && (int) g.position.y ==  (int) this.position.y)
			{
				int dmg = DamageCalc.getDamage(7000, 400, 5000, 50, 100, position.cpy());
				g.takeDamage(dmg);
				
				
			}
			
		}
		
	}
	
}
