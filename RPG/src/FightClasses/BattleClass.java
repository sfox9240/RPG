package FightClasses;
import Skills.Skill;

public abstract class BattleClass {

	protected String className;
	protected double health;
	protected int damageMod;
	protected Skill skill;
	protected int techniquePoints;
	
	public double getHealth() {
		return health;
	}

	public int getTechniquePoints() {
		return techniquePoints;
	}
	
	public int getdamageMod() {
		return damageMod;
	}

	public Skill getSkill() {
		return skill;
	}
	
}
