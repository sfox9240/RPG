package Enemies;

import java.util.List;
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
	
	public List<Item> getItems() {
		return items;
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
	
	public void attack(Character otherguy) {
		if(hitCalculator()) {
			//Attack lands
			otherguy.setHealth(otherguy.getHealth() - getCombatDmg());
			System.out.println(name + " dealt " + getCombatDmg() + " damage to " + otherguy.getName());
			if(otherguy.getHealth() <= 0) {
				System.out.println(otherguy.getName() + " has been felled!");
			}
		} else {
			//Attack missed
			System.out.println(name + " missed their attack!");
		}
	}

	public void useSpecial(Character opponent) {
		supermove.attack(this, opponent);
	}
}
