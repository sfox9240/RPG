package Character;
import java.util.Vector;

import Items.Item;
import Items.Weapon;
import FightClasses.RandomGenerator;
import Skills.Skill;

public abstract class Character {

	protected String name;
	protected double health;
	protected double maxHealth;
	protected int damageMod;
	protected Vector<Item> items = new Vector<Item>();
	protected Weapon weapon;
	protected Vector<Skill> skills = new Vector<Skill>();
	protected int techniquePoints;
	protected int maxTechniquePoints;
	
	public String getName() {
		return name;
	}
		
	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setHealth(double h) {
		health = h;
	}

	public void setMaxHealth(double mh) {
		maxHealth = mh;
	}
	
	public int getDamageMod() {
		return damageMod;
	}
	
	public Vector<Item> getItems() {
		return items;
	}

	public Vector<Skill> getSkills() {
		return skills;
	}

	public void printInventory() {
		for(int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).getName());
		}
	}
	
	public Weapon getWeapon() {
		return weapon;
	}

	public int getTechniquePoints() { return techniquePoints; }

	public void setTechniquePoints(int tp) {
		techniquePoints = tp;
	}

	public int getMaxTechniquePoints() { return maxTechniquePoints; }

	public void setMaxTechniquePoints(int mtp) {
		maxTechniquePoints = mtp;
	}
	
	public void printStatus() {
		System.out.println(this.name + "'s Health: " + health + ", TP: " + techniquePoints);
	}
	
	public abstract void attack(Character opponent);

	public void useSkill(Character opponent, int skill) {
		skills.get(skill).attack(this, opponent);
	}
	
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
