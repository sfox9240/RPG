package Enemies;

import Character.Character;
import Items.Club;

public class Hobgoblin extends Enemy {
	
	public Hobgoblin() {
		this.name = "Hobgoblin";
		this.description = "A forest Hobgoblin.";
		this.health = 14;
		this.damageMod = 1;
		this.items = null;
		this.weapon = new Club("Wooden Club", "A plain wooden club", false);
	}
}
