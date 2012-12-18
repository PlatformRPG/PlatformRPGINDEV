package com.flammantis.PlatformRPG.controllers.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.flammantis.PlatformRPG.Cooldown;
import com.flammantis.PlatformRPG.controllers.World;
import com.flammantis.PlatformRPG.models.Player;
import com.flammantis.PlatformRPG.models.Projectile;

public class Skill
{

	public String name;
	public String projectile_image;
	public int attack_rating;
	public int base_damage;
	public int cooldown;
	public Cooldown coolDown;
	
	public void execute(Player p)
	{
		if (!this.coolDown.isOnCooldown)
		{
			Texture t = new Texture(Gdx.files.internal("data/" + this.projectile_image));
			float side = p.velocity.x < 0 ? -1 : 1;
			Projectile proj = new Projectile(p.position.x, p.position.y, 0.8f, 0.8f, attack_rating, base_damage, t, side);
			proj.update();
			World.instance.projectiles.add(proj);
			p.currentState = Player.PLAYER_ATTACKING;
			this.coolDown = new Cooldown(this.cooldown);
			this.coolDown.startCooldown();
		}
		
	}
	
	public Skill(String skillName, String texture, int attack_rating, int base_damage, int cooldown)
	{
		
		this.name = skillName;
		this.projectile_image = texture;
		this.attack_rating = attack_rating;
		this.base_damage = base_damage;
		this.cooldown = cooldown;
		this.coolDown = new Cooldown(this.cooldown);
		
	}
	
	
}
