package Enemies;

import java.util.Vector;
import Character.Character;
import Items.*;

public abstract class Enemy extends Character {
	protected String description;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getHealth() {
		return health;
	}
	
	public int getDamageMod() {
		return damageMod;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon w) {
		this.weapon = w;
	}
	
	public double getCombatDmg() {
		return getDamageMod() + weapon.getDamage();
	}
}
