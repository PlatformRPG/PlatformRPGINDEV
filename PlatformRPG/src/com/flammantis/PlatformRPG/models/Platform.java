package com.flammantis.PlatformRPG.models;

import com.flammantis.PlatformRPG.DynamicGameObject;

public class Platform extends DynamicGameObject
{

	public static final float PLATFORM_WIDTH = 2;
	public static final float PLATFORM_HEIGHT = 0.5f;
	public static final int PLATFORM_TYPE_STATIC = 0;
	public static final int PLATFORM_TYPE_MOVING = 1;
	public static final float PLATFORM_VELOCITY = 2;

	int type;
	float moveX;
	float moveX2;
	

	public Platform(int type, float x, float y)
	{
		super(x, y, PLATFORM_WIDTH, PLATFORM_HEIGHT);
		this.type = type;
		if (type == PLATFORM_TYPE_MOVING)
		{
			velocity.x = PLATFORM_VELOCITY;
		}
	}
	
	public void setMovementBounds(float x, float x2)
	{
		
		this.moveX = x;
		this.moveX2 = x2;
			
	}

	public void update(float deltaTime)
	{
		if (type == PLATFORM_TYPE_MOVING)
		{
			position.add(velocity.x * deltaTime, 0);
			boundingBox.x = position.x - PLATFORM_WIDTH / 2;
			boundingBox.y = position.y - PLATFORM_HEIGHT / 2;

			if(moveX2 > position.x || position.x > moveX)
			{
				
				this.velocity.x = -this.velocity.x;
				
			}
				
		}
	}
	
	public void hitOtherPlatform()
	{
		
		this.velocity.x = -this.velocity.x;
		
	}

}
