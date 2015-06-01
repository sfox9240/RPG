package FightClasses;
import java.util.Random;


public class RandomGenerator {

	Random generator;
	
	public RandomGenerator() {
		generator = new Random();
	}
	
	public int battleRandom() {
		return generator.nextInt(2);
	}
	
	//Number between 6-9
	public int weaponDamage() {
		return generator.nextInt(3) + 2;
	}
	
	public int getNumberBetween(int min, int max) {
		return generator.nextInt(max - min + 1) + min;
	}
	//Hit calculator 50% chance
	public int attackRandom() {
		return generator.nextInt(2);
	}
}
