package com.flammantis.PlatformRPG.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flammantis.PlatformRPG.DynamicGameObject;

public class Projectile extends DynamicGameObject
{


	
	
	public int base_damage;
	public int attack_rating;
	public Texture texture;
	public TextureRegion textureRegion;
	
	public Projectile(float x, float y, float width, float height, int attack_rating, int base_damage, Texture texture, float side)
	{
		super(x, y, width, height);
		this.attack_rating = attack_rating;
		this.base_damage = base_damage;
		this.texture =  texture;
		this.velocity.x = side < 0 ? -2 : 2;
	
	}
	
	public Projectile(float x, float y, float width, float height, int attack_rating, int base_damage, TextureRegion texture, int side)
	{
		super(x, y, width, height);
		this.attack_rating = attack_rating;
		this.base_damage = base_damage;
		this.textureRegion =  texture;
		this.velocity.x = side < 0 ? -2 : 2;
		
	}
	
	

	public void update()
	{
		
		boundingBox.x = position.x - boundingBox.width / 2;
		boundingBox.y = position.y - boundingBox.height / 2;
		
		position.add(velocity.x * Gdx.graphics.getDeltaTime(), velocity.y * Gdx.graphics.getDeltaTime());
	
		
	}
	
	


	

}
