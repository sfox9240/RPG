package Character;
import FightClasses.BattleClass;
import Items.*;
import Skills.DoubleStrike;
import Skills.Fireball;

public class Hero extends Character {
	protected BattleClass fightclass;
	
	public Hero(String name, BattleClass type) {
		this.name = name;
		fightclass = type;
		weapon = null;
		health = fightclass.getHealth();
		techniquePoints = fightclass.getTechniquePoints();
		supermove = fightclass.getSkill();
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

	public void useSpecial(Character opponent) {
		supermove.attack(this, opponent);
	}

	@Override
	public void attack(Character opponent) {
		Boolean hit = hitCalculator();
		if(hit) {
			//Attack lands
			opponent.setHealth(opponent.getHealth() - getCombatDmg());
			System.out.println(name + " dealt " + getCombatDmg() + " damage to " + opponent.getName());
			if(opponent.getHealth() <= 0) {
				System.out.println(opponent.getName() + " has been felled!");
			}
		} else {
			//Attack missed
			System.out.println(name + " missed their attack!");
		}
	}
}
