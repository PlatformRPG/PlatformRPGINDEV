package com.flammantis.PlatformRPG;

public class Cooldown
{

	public int cooldown; // This cooldown's time (in seconds)
	public boolean isOnCooldown; // Currently on cooldown?
	public int timeOnCooldown; // Time we have been on this cooldown ( in seconds )
	public long timeStartCooldown; // The time when we started the cooldown ( in seconds)
	
	public Cooldown(int cooldown)
	{

		this.cooldown = cooldown;
		
	}

	// Let's start our cooldown
	public void startCooldown()
	{
		
		this.isOnCooldown = true;
		this.timeStartCooldown = System.currentTimeMillis();
		
	}
	
	// Update method; check
	public void update()
	{
		if (this.isOnCooldown)
		{
			long diff = (System.currentTimeMillis() - this.timeStartCooldown);
			if (diff >= (cooldown * 1000))
			{
				
				this.isOnCooldown = false;
				
			}
		}
		
	}
	
	
	
}
