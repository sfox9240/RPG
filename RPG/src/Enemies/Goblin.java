package Enemies;
import Items.*;

public class Goblin extends Enemy {

	public Goblin() {
		this.name = "Goblin";
		this.description = "A weak forest goblin.";
		this.health = 11;
		this.maxHealth = 11;
		this.techniquePoints = 0;
		this.maxTechniquePoints = 0;
		this.damageMod = 0;
		this.items = null;
		this.weapon = new Club("Wooden Club", "A plain wooden club", false);
	}
}
