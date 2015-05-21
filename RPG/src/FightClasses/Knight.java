package FightClasses;


public class Knight extends BattleClass {

	protected RandomGenerator fightMachine = new RandomGenerator();
	
	public Knight() {
		className = "Knight";
		health = 35.0;
		damageMod = fightMachine.battleRandom() + 3;
	}
}
