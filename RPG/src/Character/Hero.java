package Character;
import FightClasses.BattleClass;
import Items.*;
import FightClasses.RandomGenerator;

public class Hero extends Character {
	protected BattleClass fightclass;
	
	public Hero(String name, BattleClass type) {
		this.name = name;
		this.fightclass = type;
		this.weapon = null;
		this.health = fightclass.getHealth();
	}
	
	public int getDamageMod() {
		return fightclass.getdamageMod();
	}
	
	public void setWeapon(Weapon w) {
		this.weapon = w;
	}
	
	public double getCombatDmg() {
		return getDamageMod() + weapon.getDamage();
	}

	@Override
	public void attack(Character otherguy) {
		Boolean hit = hitCalculator();
		if(hit) {
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
}
