package com.flammantis.PlatformRPG.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.flammantis.PlatformRPG.MusicQueue;
import com.flammantis.PlatformRPG.controllers.Assets;
import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.controllers.World.WorldListener;
import com.flammantis.PlatformRPG.controllers.Skills.Skill;
import com.flammantis.PlatformRPG.models.Player;
import com.flammantis.PlatformRPG.models.WorldRenderer;

public class GameScreen implements Screen
{

	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;

	Game theGame;
	
	float slashCooldown = 0;

	public OrthographicCamera guiCam;
	Vector3 touchPoint;
	public SpriteBatch batch;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	Rectangle pauseBounds;
	Rectangle leftBounds;
	Rectangle rightBounds;
	Rectangle upBounds;

	int state = 1;

	boolean flickerDraw = true;
	int lastFlicker = 0;
	
	MusicQueue tracks;

	public GameScreen(Game theGame)
	{

		this.theGame = theGame;
		Array<Music> trackList = new Array<Music>();
		trackList.add(Assets.faire);
		trackList.add(Assets.alchemist);
		trackList.add(Assets.theoldwizard);
		this.tracks = new MusicQueue(trackList);
		guiCam = new OrthographicCamera(320, 480);
		guiCam.position.set(320 / 2, 480 / 2, 0);
		touchPoint = new Vector3();
		batch = new SpriteBatch();
		worldListener = new WorldListener()
		{
			@Override
			public void jump()
			{
				world.thePlayer.onGround = false;
				world.thePlayer.lastHurtTime = 0;
				world.thePlayer.hasBeenHit = false;
			}

			@Override
			public void takeDamage(int amount)
			{

				world.thePlayer.damage(amount);

			}

			@Override
			public void loot()
			{

			}

		};
		world = new World(worldListener, theGame);
		renderer = new WorldRenderer(batch, world);
		pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
		upBounds = new Rectangle(320 - 51, 120, 50, 50);
		rightBounds = new Rectangle(320 - 51, 70, 50, 50);
		leftBounds = new Rectangle(320 - 51, 0, 50, 50);
		
	}

	@Override
	public void render(float delta)
	{

		update(delta);
		draw(delta);

	}

	private void draw(float delta)
	{

		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderer.render();

		guiCam.update();
		batch.setProjectionMatrix(guiCam.combined);
		batch.enableBlending();
		batch.begin();

		if (state == GameScreen.GAME_PAUSED)
			batch.draw(Assets.play, 320 - 64, 480 - 64, 50, 50);
		else
			batch.draw(Assets.pause, 320 - 64, 480 - 64, 50, 50);
		batch.draw(Assets.left, 320 - 51, 0, 50, 50);
		batch.draw(Assets.right, 320 - 51, 70, 50, 50);
		batch.draw(Assets.up, 320 - 51, 120, 50, 50);
		//batch.draw(Assets.potEmpty, 20, 50);
		//batch.draw(new Texture(Gdx.files.internal("data/swordrangeattack.png")), 20, 50);
		Texture slot1Texture = Assets.skillSlotEmpty;
		Texture slot2Texture = Assets.skillSlotEmpty;
		Texture slot3Texture = Assets.skillSlotEmpty;
		boolean slot1cooldown = false;
		boolean slot2cooldown = false;
		boolean slot3cooldown = false;
		
		int count = 0;
		for (int key : world.skillsOnBar.keySet())
		{
			
			Skill s = world.skillsOnBar.get(key);
			switch (count)
			{
			
			case 0:
				slot1Texture = new Texture(Gdx.files.internal("data/" + s.projectile_image));
				if (s.coolDown.isOnCooldown)
					slot1cooldown = true;
				break;

			case 1:
				slot2Texture = new Texture(Gdx.files.internal("data/" + s.projectile_image));
				if (s.coolDown.isOnCooldown)
					slot2cooldown = true;
				break;

			case 2:
				slot3Texture = new Texture(Gdx.files.internal("data/" + s.projectile_image));
				if (s.coolDown.isOnCooldown)
					slot3cooldown = true;
				break;
			
			}
			count++;
			
		}
		int x = 115;
		for (int i = 0; i < 3; i++)
		{
			
			batch.draw(Assets.skillSlotFrame, x, 5);
			x += 30;
			
		}
		batch.draw(slot1Texture, 113, 5);
		batch.draw(slot2Texture, 145, 5);
		batch.draw(slot3Texture, 175, 5);
		if (slot1cooldown)
			batch.draw(Assets.skillSlotOverlay, 115, 5);
		if (slot2cooldown)
			batch.draw(Assets.skillSlotOverlay, 145, 5);
		if (slot3cooldown)
			batch.draw(Assets.skillSlotOverlay, 175, 5);
		batch.end();
		renderHP();

	}

	private void renderHP()
	{
		ShapeRenderer render = new ShapeRenderer();

		int hp = world.thePlayer.hp;

		if (hp >= 50)
			render.setColor(0.0f, 1.0f, 0.0f, 1);
		else if (hp > 33)
			render.setColor(1.0f, 1.0f, 0.0f, 1);
		else if (hp > 0)
			render.setColor(1.0f, 0.0f, 0.0f, 1);

		if (!(hp < 33))
		{
			render.begin(ShapeRenderer.ShapeType.FilledRectangle);
			render.filledRect(5, 5, hp * 2, 20);
			render.end();

		}

		if (flickerDraw && (hp < 33))
		{
			render.begin(ShapeRenderer.ShapeType.FilledRectangle);
			render.filledRect(5, 5, hp * 2, 20);
			render.end();
			flickerDraw = false;
		} else if (lastFlicker > 3)
		{
			flickerDraw = true;
			this.lastFlicker = 0;
		} else
			this.lastFlicker++;

	}

	private void update(float delta)
	{

		switch (state)
		{

		case GameScreen.GAME_PAUSED:
			updatePaused();
			break;

		case GameScreen.GAME_RUNNING:
			updateRunning();

		}
		
		this.tracks.update();

	}

	private void updatePaused()
	{

		if (Gdx.input.justTouched())
		{
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (MainMenuScreen.pointInRectangle(pauseBounds, touchPoint.x,
					touchPoint.y))
			{

				state = GAME_RUNNING;
				return;
			}

		}

	}

	private void updateRunning()
	{
		float accel = 0;
		if (Gdx.input.justTouched())
		{
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (MainMenuScreen.pointInRectangle(pauseBounds, touchPoint.x,
					touchPoint.y))
			{
				state = GAME_PAUSED;
				return;
			}

			

			if (MainMenuScreen.pointInRectangle(upBounds, touchPoint.x,
					touchPoint.y)
					&& MainMenuScreen.pointInRectangle(upBounds, touchPoint.x,
							touchPoint.y)
					&& world.thePlayer.onGround
					|| MainMenuScreen.pointInRectangle(upBounds, touchPoint.x,
							touchPoint.y) && world.thePlayer.position.y == 0)
			{

				world.listener.jump();
				world.thePlayer.currentState = Player.PLAYER_JUMPING;
				world.thePlayer.velocity.y = Player.JUMP_VELOCITY;

				
			}
		}
		
		if (Gdx.input.isTouched())
		{
			
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));
			
			if (MainMenuScreen.pointInRectangle(leftBounds, touchPoint.x,
					touchPoint.y))
			{
				accel = 5f;
				world.thePlayer.currentState = Player.PLAYER_WALKING;
				
			}
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (MainMenuScreen.pointInRectangle(rightBounds, touchPoint.x,
					touchPoint.y))
			{
				accel = -5f;
				world.thePlayer.currentState = Player.PLAYER_WALKING;
				
			}
			
		}

		if (!(world.thePlayer.currentState == Player.PLAYER_ATTACKING))
		{
			if (Gdx.input.isKeyPressed(Keys.A))
			{

				accel = 5f;
				world.thePlayer.currentState = Player.PLAYER_WALKING;

			}
			if (Gdx.input.isKeyPressed(Keys.D))
			{

				accel = -5f;
				world.thePlayer.currentState = Player.PLAYER_WALKING;

			}
			if (Gdx.input.isKeyPressed(Keys.SPACE)
					&& world.thePlayer.onGround
					|| Gdx.input.isKeyPressed(Keys.SPACE)
					&& world.thePlayer.position.y == 0)
			{

				world.listener.jump();
				world.thePlayer.currentState = Player.PLAYER_JUMPING;
				world.thePlayer.velocity.y = Player.JUMP_VELOCITY;

			} 
			
			else if (accel == 0f)
			{

				
				world.thePlayer.currentState = Player.PLAYER_IDLE;

			}	
		}
		
		
	  if (Gdx.input.isKeyPressed(Keys.X) && this.slashCooldown > 0.5f && this.world.thePlayer.currentState != Player.PLAYER_ATTACKING)
	  {

		this.world.thePlayer.currentState = Player.PLAYER_ATTACKING;
		this.world.thePlayer.attackWithSword();
		this.slashCooldown = 0f;
	  } 
	  else
		  this.slashCooldown += Gdx.graphics.getDeltaTime();
	  
	  for (int i : world.skillsOnBar.keySet())
	  {
		  
		  Skill s = world.skillsOnBar.get(i);
		  if (Gdx.input.isKeyPressed(i) && !s.coolDown.isOnCooldown)
		  {
			  
			  s.execute(world.thePlayer);
			  
		  }
		  
		  
	  }
		
	  if (Gdx.input.isKeyPressed(Keys.A))
		{

			accel = 5f;
			

		}
		if (Gdx.input.isKeyPressed(Keys.D))
		{

			accel = -5f;
			

		}
	  
		world.update(Gdx.graphics.getDeltaTime(), accel);

	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void show()
	{

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

}
