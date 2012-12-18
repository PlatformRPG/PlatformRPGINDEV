package com.flammantis.PlatformRPG;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A game object. Static (cannot move)
 * 
 * @author Flammantis
 *
 */

public class GameObject
{

	public Vector2 position;
	public Rectangle boundingBox;
	
	public GameObject(float x, float y, float width, float height)
	{
		
		this.position = new Vector2(x, y);
		this.boundingBox = new Rectangle(x - width / 4, y - height / 4, width, height);
		
	}
	
}
