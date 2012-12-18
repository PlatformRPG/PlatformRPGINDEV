package com.flammantis.PlatformRPG;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.flammantis.PlatformRPG.models.WorldRenderer;

public class DamageCalc
{

	public static int getDamage(int attack, int defense, int dodge, int crit, int weaponhitrate, Vector2 pos)
	{
		
		Random rand = new Random();
		
		int critChanceRand = rand.nextInt(101);
		int hitChanceRand = rand.nextInt(101);
		int chanceToHit = (attack - dodge) / (weaponhitrate + 50) + weaponhitrate;
		int damage = (attack - defense)-(weaponhitrate - (rand.nextInt(6) + 1));
		int critChance = (chanceToHit / crit) + crit/4;
		
		if (chanceToHit > 100)
			chanceToHit = 100;
			
		if (critChance > 100)
			critChance = 100;
		
		boolean isCrit = critChanceRand <= critChance;
		
		if (hitChanceRand <= chanceToHit)
		{
			if (!isCrit)
			{
				
				WorldRenderer.instance.damages.add(new DamageText("" + damage, pos.cpy()));
				System.out.println(damage);
				return damage;
			
			}
			else
			{
				WorldRenderer.instance.damages.add(new DamageText("CRIT!: " + damage*2, pos.cpy()));
				System.out.println(damage*2);
				return damage*2;
			
			}
		}
		else
		{
			
			WorldRenderer.instance.damages.add(new DamageText("MISS!", pos.cpy()));
			return 0;
			
		}
		
	}
	
}
