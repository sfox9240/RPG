package Character;
import FightClasses.BattleClass;
import Game.Status;
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
		maxHealth = fightclass.getHealth();
		techniquePoints = fightclass.getTechniquePoints();
		maxTechniquePoints = fightclass.getTechniquePoints();
		skills.add(fightclass.getSkill());
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
}
