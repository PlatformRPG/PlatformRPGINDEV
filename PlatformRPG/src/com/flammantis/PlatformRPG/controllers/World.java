package com.flammantis.PlatformRPG.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.flammantis.PlatformRPG.Cooldown;
import com.flammantis.PlatformRPG.DamageCalc;
import com.flammantis.PlatformRPG.controllers.Skills.Skill;
import com.flammantis.PlatformRPG.models.Goblin;
import com.flammantis.PlatformRPG.models.Platform;
import com.flammantis.PlatformRPG.models.Player;
import com.flammantis.PlatformRPG.models.Projectile;

/**
 * The World.
 * 
 * @author Flammantis
 *
 */


public class World
{

	public interface WorldListener
	{
		// player jumps
		public void jump ();
		// player hit
		public void takeDamage (int amount);
		// coming later :)
		public void loot ();
	}
	
	public static final float WORLD_WIDTH = 150;
	public static final float WORLD_HEIGHT = 50;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);
	public final WorldListener listener;
	
	public static World instance;
	
	public final ArrayList<Platform> platforms;
	
	public final Random rand;
	
	public final Player thePlayer;
	public ArrayList<Goblin> goblins;
	
	public int enemiesOnPlayer = 0;
	public ArrayList<Projectile> projectiles;
	public Game game;
	
	public HashMap<Integer, Skill> skillsOnBar;
	
	

	
	public World(WorldListener listener, Game game)
	{
		World.instance = this;
		thePlayer = new Player(5, 1);
		this.listener = listener;
		this.platforms = new ArrayList<Platform>();
		this.goblins = new ArrayList<Goblin>();
		this.projectiles = new ArrayList<Projectile>();
		this.skillsOnBar = new HashMap<Integer, Skill>();
		this.rand = new Random();
		this.generatePlatforms();
		this.platforms.add(new Platform(0, 5, 1));
		this.game = game;
		Skill s = null;
		for (Skill s1 : Assets.skills.skills)
		{
			
			if (s1.name.equals("Sword Wave"))
				s = new Skill(s1.name, s1.projectile_image, s1.attack_rating, s1.base_damage, s1.cooldown);
			
		}
		if (s != null)
		{
			this.skillsOnBar.put(Keys.Q, s);
		}
		
		
	
		
		
	}
	
	
	
	public void generatePlatforms()
	{
		
		float y = Platform.PLATFORM_HEIGHT / 2;
	
		
		for (int i = 0; i < 50; i++)
		{
			int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
			float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

			for (Platform p : platforms)
			{
				
				if (p.position.y - y < 3)
					if (rand.nextInt() < 3) y += p.position.y - y + 3; else y -= p.position.y - y + 3;
				if (p.position.x - x < 6)
					x += p.position.x - x + 5;
				
			}
			
			
			if (y < 0)
				y = 5;
			if (y == 0)
				y += rand.nextInt(100);
			
			
			
			Platform platform = new Platform(type, x, y);
			 platform.setMovementBounds(x + 3, x - 3);
			platforms.add(platform);
			
			if ( rand.nextFloat() < 1) {
				Goblin goblin = new Goblin(platform.position.x + rand.nextFloat(), platform.position.y
					+ Goblin.HEIGHT + rand.nextFloat() * 2);
				goblins.add(goblin);
			}

			
			y += rand.nextInt(5);
			y -= rand.nextInt(5);
			
			
			
			
			
		}
		
	}
	
	public void update(float deltaTime, float accelX)
	{
		
		this.updatePlayer(deltaTime, accelX);
		checkCollisions();
		this.updatePlatforms();
		this.updateGoblins();
		this.updateProjectiles();
		this.updateCooldowns();	
		
	}

	private void updateCooldowns()
	{
		if (this.skillsOnBar.size() > 0)
		{
			for (int i : this.skillsOnBar.keySet())
			{
				if (this.skillsOnBar.get(i) != null)
				{
					if (this.skillsOnBar.get(i).coolDown != null)
					{
						Cooldown c = this.skillsOnBar.get(i).coolDown;
						c.update();
					}
				}
				
			}
		}
		
	}



	private void updateProjectiles()
	{
		
		for (Projectile p : projectiles)
		{
			
			p.update();
			
		}
		
	}



	private void updateGoblins()
	{
		
		for (Goblin g : goblins)
		{
			
			g.update(Gdx.graphics.getDeltaTime());
			
		}
		
	}

	private void checkCollisions()
	{
		
		checkPlatformCollisions();
		checkGoblinCollisions();
		checkProjectileCollisions();
		
	}

	private void checkProjectileCollisions()
	{
		
		ArrayList<Projectile> projectiles = (ArrayList<Projectile>) this.projectiles.clone();
		ArrayList<Goblin> goblins = (ArrayList<Goblin>) this.goblins.clone();
		
		for (Projectile p : projectiles)
		{
			
			for (Goblin g : goblins)
			{
				
				if (p.boundingBox.overlaps(g.boundingBox))
				{
					
					int dmg = DamageCalc.getDamage(7000, 400, 5000, 50, 100, p.position.cpy());
					g.takeDamage(dmg);
					this.projectiles.remove(p);
					
					
				}
				
			}
			
		}
		
	}



	private void checkPlatformCollisions()
	{
		boolean isColliding = false;
		
		for (Platform p : platforms)
		{
			
			if (p.boundingBox.overlaps(thePlayer.boundingBox) && thePlayer.boundingBox.y > (p.boundingBox.y))
			{
				
				thePlayer.hitPlatform();
				isColliding = true;
				
			}
			else if (p.boundingBox.overlaps(thePlayer.boundingBox) && thePlayer.boundingBox.y < (p.boundingBox.y))
			{
				
				thePlayer.velocity.y = -Player.JUMP_VELOCITY / 2;
				
			}
			
			for (Goblin e : this.goblins)
			{
				
				e.colliding = false;
				
				if (p.boundingBox.overlaps(e.boundingBox) && e.boundingBox.y > (p.boundingBox.y))
				{
					
					e.hitPlatform();
					e.colliding = true;
					
				}
				
				
			}
			
			for (Platform p2 : platforms)
			{
				
				if (p.boundingBox.overlaps(p2.boundingBox))
				{
					p.hitOtherPlatform();
					p2.hitOtherPlatform();
				}
				
			}
			
			
			
		}
		
		if (!isColliding && thePlayer.position.y != 0)
		{
			
			thePlayer.onGround = false;
			
		}
		
	}
	private void checkGoblinCollisions()
	{
		
		
		for (Goblin g : goblins)
		{
			
			if (g.boundingBox.overlaps(thePlayer.boundingBox))
			{
				
				g.hitPlayer(thePlayer);
				
				
			}
			
			for (Goblin e : goblins)
			{
				
				if (e.boundingBox.overlaps(g.boundingBox))
				{
					
					e.velocity.x = 0;
					
					
				}
				
			}

		}
		
		
		
	}

	public void updatePlatforms()
	{
		
		for (Platform p : platforms)
			p.update(Gdx.graphics.getDeltaTime());
		
	}
	
	private void updatePlayer (float deltaTime, float accelX) 
	{
		
		 thePlayer.velocity.x = -accelX / 10 * Player.MOVE_VELOCITY;
		 thePlayer.update(deltaTime);
		 
		 if (thePlayer.position.y == 0)
		 {
			 thePlayer.onGround = true;
			 thePlayer.currentState = Player.PLAYER_IDLE;
		 }
		 
		 if (thePlayer.position.y < 0)
		 {
			 
			 thePlayer.position.y = 0;
			 
		 }
			 
		
	}

	
	
}
