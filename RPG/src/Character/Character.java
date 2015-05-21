package Character;
import java.util.List;

import Items.Item;
import Items.Weapon;
import FightClasses.RandomGenerator;

public abstract class Character {

	protected String name;
	protected double health;
	protected int damageMod;
	protected List<Item> items;
	protected Weapon weapon;
	
	public String getName() {
		return name;
	}
		
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double h) {
		health = h;
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
	
	public void printStatus() {
		System.out.println(this.name + "'s Health: " + getHealth());
	}
	
	public abstract void attack(Character otherguy);
	
	protected Boolean hitCalculator() {
		RandomGenerator generator = new RandomGenerator();
		int hit = generator.attackRandom();
		if(hit == 1) {
			return true;
		} else {
			return false;
		}
	}
}
