package FightClasses;

import Skills.FireBall;

public class Wizard extends BattleClass {
	protected RandomGenerator fightMachine = new RandomGenerator();;
	
	public Wizard() {
		className = "Wizard";
		health = 25.0;
		damageMod = fightMachine.battleRandom() + 1;
		skill = new FireBall();
		techniquePoints = 45;
	}
}
