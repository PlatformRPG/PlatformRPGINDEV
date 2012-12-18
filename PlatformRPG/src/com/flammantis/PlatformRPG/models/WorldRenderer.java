package com.flammantis.PlatformRPG.models;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flammantis.PlatformRPG.DamageText;
import com.flammantis.PlatformRPG.controllers.Assets;
import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.controllers.Skills.Skill;

public class WorldRenderer
{

	static final float WIDTH = 10;
	static final float HEIGHT = 15;
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	TextureRegion background;
	BitmapFont font;
	public ArrayList<DamageText> damages;
	int lastGoblinAnim = 0;
	
	public static WorldRenderer instance;

	public WorldRenderer(SpriteBatch batch, World world)
	{
		this.world = world;
		this.cam = new OrthographicCamera(WIDTH, HEIGHT);
		this.cam.position.set(WIDTH / 2, HEIGHT / 2, 0);
		this.batch = batch;
		this.damages = new ArrayList<DamageText>();
		this.font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font_00.png"), false);
		instance = this;
		
	}

	public void render()
	{
		batch.begin();
		if (world.thePlayer.position.y != cam.position.y)
			cam.position.y = world.thePlayer.position.y;
		if (world.thePlayer.position.x != cam.position.x)
			cam.position.x = world.thePlayer.position.x;
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
		renderTexts();
		renderSkills();
		batch.end();
		
		
		
	}

	private void renderSkills()
	{
	
		
		
	}

	private void renderTexts()
	{
		ArrayList<DamageText> ds = (ArrayList<DamageText>) damages.clone();
		for (DamageText d : ds)
		{
			d.pos.y += 0.1f;
			this.font.draw(batch, d.text, d.pos.x, d.pos.y);
			if (d.timeOn > 5)
			{
				this.damages.remove(d);
			}
			else
			{
				d.timeOn += Gdx.graphics.getDeltaTime();
			}
			
			
		}
		
	}

	private void renderObjects()
	{
		
		renderPlayer();
		renderPlatforms();
		renderGoblins();
		renderProjectiles();

	}

	private void renderProjectiles()
	{
	
		for (Projectile p : world.projectiles)
		{
			
			float side = p.velocity.x < 0 ? -1 : 1;
			if (side < 0)
			{
				if (p.texture != null)
					batch.draw(p.texture, p.position.x + 0.5f, (p.position.y - 0.5f) + 0.0f, side * 1, 1.3f);
				else if (p.textureRegion != null)
					batch.draw(p.texture, p.position.x - 0.5f, (p.position.y - 0.5f) + 0.0f, side * 1, 1.3f);
			}
			else
			{
				if (p.texture != null)
					batch.draw(p.texture, p.position.x + 0.5f, (p.position.y - 0.5f) + 0.0f, side * 1, 1.3f);
				else if (p.textureRegion != null)
					batch.draw(p.texture, p.position.x - 0.5f, (p.position.y - 0.5f) + 0.0f, side * 1, 1.3f);
					
			}
			
			
			
			
		}
		
	}

	private void renderGoblins()
	{
		
		for (Goblin g : world.goblins)
		{
			TextureRegion pic = Assets.goblinIdle;
	
			float side = g.velocity.x < 0 ? -1 : 1;
			if (side < 0)
				batch.draw(pic, g.position.x + 0.5f, (g.position.y - 0.5f) + 0.0f, side * 1, 1);
			else
				batch.draw(pic, g.position.x - 0.5f, (g.position.y - 0.5f) + 0.0f, side * 1, 1);
		}
		
	}

	private void renderPlatforms()
	{
		 
		for (Platform p : world.platforms)
		{
			
			batch.draw(Assets.platform, p.position.x - 1, p.position.y - 0.25f, 2, 0.5f);
			
		}
		
	}

	private void renderPlayer()
	{
		Texture pic = null;
		switch (world.thePlayer.currentState)
		{
		
	/*	case Player.PLAYER_WALKING:
			pic = Assets.playerWalk.get((int) (world.thePlayer.timeInState % 4));
			break;*/
	
		case Player.PLAYER_ATTACKING:
			pic = Assets.playerAttack.get(0);
			break;
			
		default:
			pic = Assets.playerIdle;
			
		
		}

		float side = world.thePlayer.velocity.x < 0 ? -1 : 1;
		if (side < 0)
			batch.draw(pic, world.thePlayer.position.x + 0.5f, (world.thePlayer.position.y - 0.5f) + 0.0f, side * 1, 1.3f);
		else
			batch.draw(pic, world.thePlayer.position.x - 0.5f, (world.thePlayer.position.y - 0.5f) + 0.0f, side * 1, 1.3f);
		
	}

	private void renderBackground()
	{
		
		
		batch.draw(Assets.backgroundRegion, cam.position.x - WIDTH / 2, cam.position.y - HEIGHT / 2, WIDTH, HEIGHT);
		batch.draw(Assets.ground, -8f, -8f, 480, 7.5f);
		
		
	}

}
