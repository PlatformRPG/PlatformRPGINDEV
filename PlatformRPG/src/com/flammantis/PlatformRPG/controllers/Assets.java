package com.flammantis.PlatformRPG.controllers;

import java.io.File;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.flammantis.PlatformRPG.Skills;
import com.flammantis.PlatformRPG.controllers.Skills.Skill;
import com.thoughtworks.xstream.XStream;

public class Assets
{

	public static Texture pics;

	public static Texture background;
	public static TextureRegion backgroundRegion;

	public static Texture ground;
	public static TextureRegion groundRegion;

	public static Array<Texture> playerWalk = new Array<Texture>();
	public static Array<TextureRegion> goblinWalk = new Array<TextureRegion>();
	public static Array<Texture> playerAttack = new Array<Texture>();
	public static Skills skills;

	public static Texture playerIdle;
	public static TextureRegion goblinIdle;

	public static Music faire;
	public static Music theoldwizard;
	public static Music alchemist;

	public static Texture pause;
	public static Texture play;
	public static Texture left;
	public static Texture right;
	public static Texture up;
	public static TextureRegion fireball;

	public static TextureRegion platform;
	public static TextureRegion title;

	public static Sound hurtSound;

	public static Texture potFull;

	public static Texture potEmpty;
	public static Texture skillSlotEmpty;
	public static Texture skillSlotFrame;

	public static Texture skillSlotOverlay;  

	
	
	public static void loadPics()
	{
		loadSkills();
		background = getTexture("data/background.jpg");
		backgroundRegion = new TextureRegion(background, 0, 0, 480, 320);

		ground = getTexture("data/grass.png");
		groundRegion = new TextureRegion(ground, 0, 0, 480, 20);

		pics = getTexture("data/spritesheetswordsman.png");

		playerIdle = new Texture(Gdx.files.internal("data/playeridle.png"));

		playerAttack.add(new Texture(Gdx.files.internal("data/slash3.png")));

		alchemist = Gdx.audio.newMusic(Gdx.files
				.internal("data/alchemistsbacksound.mp3"));
		alchemist.setVolume(0.5f);
		faire = Gdx.audio.newMusic(Gdx.files.internal("data/faire.mp3"));
		faire.setVolume(0.5f);
		theoldwizard = Gdx.audio.newMusic(Gdx.files
				.internal("data/oldwizard.mp3"));
		alchemist.setVolume(0.5f);

		goblinIdle = new TextureRegion(pics, 122, 2, 32, 32);
		
		potFull = new Texture(Gdx.files.internal("data/full.png"));
		potEmpty = new Texture(Gdx.files.internal("data/potionempty.png"));
		
		skillSlotEmpty = new Texture(Gdx.files.internal("data/empty.png"));
		
		skillSlotFrame = new Texture(Gdx.files.internal("data/frame.png"));
		skillSlotOverlay = new Texture(Gdx.files.internal("data/overlay.png"));
		
		title = new TextureRegion(pics, 0, 46, 250, 62);

		play = new Texture(Gdx.files.internal("data/play.png"));

		left = new Texture(Gdx.files.internal("data/left.png"));
		right = new Texture(Gdx.files.internal("data/right.png"));
		up = new Texture(Gdx.files.internal("data/up.png"));
		fireball = new TextureRegion(new Texture(
				Gdx.files.internal("data/fireball.png")), 32, 32);
		pause = new Texture(Gdx.files.internal("data/pause.png"));

		platform = new TextureRegion(pics, 154, 0, 62, 14);

		hurtSound = Gdx.audio.newSound(Gdx.files.internal("data/hurt.mp3"));
		

	}

	private static void loadSkills()
	{

		XStream xstream = new XStream();
		xstream.alias("skill", Skill.class);
		xstream.alias("name", String.class);
		xstream.alias("projectile_image", String.class);
		xstream.alias("attack_rating", Integer.class);
		xstream.alias("base_damage", Integer.class);
		xstream.alias("cooldown", Integer.class);
		xstream.alias("skills", Skills.class);
		xstream.addImplicitCollection(Skills.class, "skills");
		File loc = new File("D:/libgdxgames/PlatformRPG-android/assets/data/skills.xml");
		
		
		skills = (Skills) xstream.fromXML(loc);
		System.out.println(skills.skills.get(0).cooldown);
		
		

	}

	public static Texture getTexture(String fileName)
	{
		return new Texture(Gdx.files.internal(fileName));
	}

	public static void dispose()
	{

		pics.dispose();
		background.dispose();
		play.dispose();
		pause.dispose();

	}

}
