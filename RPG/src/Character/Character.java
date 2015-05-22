package Character;
import java.util.List;

import Items.Item;
import Items.Weapon;
import FightClasses.RandomGenerator;
import Skills.Skill;

public abstract class Character {

	protected String name;
	protected double health;
	protected int damageMod;
	protected List<Item> items;
	protected Weapon weapon;
	protected Skill supermove;
	protected int techniquePoints;
	
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

	public int getTechniquePoints() { return techniquePoints; }

	public void setTechniquePoints(int tp) {
		techniquePoints = tp;
	}
	
	public void printStatus() {
		System.out.println(this.name + "'s Health: " + health + ", TP: " + techniquePoints);
	}
	
	public abstract void attack(Character opponent);

	public abstract void useSpecial(Character opponent);
	
	public Boolean hitCalculator() {
		RandomGenerator generator = new RandomGenerator();
		int hit = generator.attackRandom();
		if(hit == 1) {
			return true;
		} else {
			return false;
		}
	}
}
