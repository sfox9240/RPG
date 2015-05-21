package FightClasses;

public class Wizard extends BattleClass {
	protected RandomGenerator fightMachine = new RandomGenerator();;
	
	public Wizard() {
		className = "Wizard";
		health = 25.0;
		damageMod = fightMachine.battleRandom() + 1;
	}
}
