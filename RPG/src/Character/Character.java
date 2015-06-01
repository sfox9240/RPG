package Character;
import java.util.Vector;

import Game.Illness;
import Game.Status;
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
	protected Illness illness = Illness.NONE;
	protected Status status = Status.NORMAL;
	
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

	public abstract double getCombatDmg();

	public Illness getIllness() {
		return illness;
	}

	public void setIllness(Illness i) {
		illness = i;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status s) {
		status = s;
	}

	public void useSkill(Character target, int skill) {
		skills.get(skill).attack(this, target);
	}

	public void useItem(Character target, int item) { items.get(item).use(this, target);}
	
	public Boolean hitCalculator() {
		RandomGenerator generator = new RandomGenerator();
		int hit = generator.attackRandom();
		if(hit == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void attack(Character opponent) {
		Boolean hit = hitCalculator();
		if(hit) {
			//Attack lands
			if(opponent.getStatus() == Status.GUARDING) { //Guarding halves the amount of damage delivered
				opponent.setHealth(opponent.getHealth() - (getCombatDmg() / 2));
				System.out.println(opponent.getName() + " guarded against the attack!");
				System.out.println(name + " dealt " + (getCombatDmg() / 2) + " damage to " + opponent.getName());
			} else {
				opponent.setHealth(opponent.getHealth() - getCombatDmg());
				System.out.println(name + " dealt " + getCombatDmg() + " damage to " + opponent.getName());
			}

			if(opponent.getHealth() <= 0) {
				System.out.println(opponent.getName() + " has been felled!");
				opponent.setStatus(Status.FAINTED);
			}
		} else {
			//Attack missed
			System.out.println(name + " missed their attack!");
		}
	}
}
