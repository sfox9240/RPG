package Enemies;
import Items.*;

public class Guard extends Enemy{

	public Guard() {
		this.name = "Guard";
		this.description = "A palace guard";
		this.health = 21;
		this.damageMod = 2;
		this.items = null;
		this.weapon = new Sword("Iron Sword", "An iron sword. Standard issue.", false);
	}
}
