package com.flammantis.PlatformRPG;

import com.badlogic.gdx.math.Vector2;

/**
 * A dynamic game object. Capable of moving
 * 
 * @author Flammantis
 *
 */

public class DynamicGameObject extends GameObject
{

	public final Vector2 velocity;
	public final Vector2 acceleration;
	
	public boolean onGround = true;
	
	public DynamicGameObject(float x, float y, float width, float height)
	{
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		this.velocity = new Vector2();
		this.acceleration = new Vector2();
	}

}
