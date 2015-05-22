package Game;

import java.util.Vector;

import Augments.*;
import Character.Hero;
import Enemies.*;
import FightClasses.*;
import Items.*;
public class main {

	public static void main(String[] args) {
		 /*
		 * This is just a testing ground for methods and different functionality
		 */
		
		CombatHandler BATTLE = new CombatHandler();
		Hero Link = new Hero("Link", new Knight());
		Hero Zelda = new Hero("Zelda", new Wizard());
		
		System.out.println("Link Health: " + Link.getHealth());
		System.out.println("Link dmg mod: " + Link.getDamageMod());
		System.out.println("Zelda Health: " + Zelda.getHealth());
		System.out.println("Zelda dmg mod: " + Zelda.getDamageMod());
		
		Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
		Bow woodenBow = new Bow("Wooden Bow", "A nice wooden bow", true);

		Link.setWeapon(woodenSword);
		Zelda.setWeapon(woodenBow);
		
		Link.getWeapon().getDetails();
		Zelda.getWeapon().getDetails();
		
		System.out.println("Link's combat damage is: " + Link.getCombatDmg());
		System.out.println("Zelda's combat damage is: " + Zelda.getCombatDmg());
		
		Vector<Hero> party = new Vector<Hero>();
		party.add(Link);
		party.add(Zelda);
		
		Vector<Enemy> enemies = new Vector<Enemy>();
		
		Guard g1 = new Guard(); enemies.add(g1);
		Guard g2 = new Guard(); enemies.add(g2);
		//Guard g3 = new Guard(); enemies.add(g3);
		//Goblin g1 = new Goblin();
		//Goblin g2 = new Goblin();
		Hobgoblin h1 = new Hobgoblin();
		
		Club megaclub = new Club("Wooden Club", "A club", false);
		Lightning zap = new Lightning(new BaseAugment());
		megaclub.setAugment(zap);
		
		h1.setWeapon(megaclub);
		
		//enemies.add(g1);
		enemies.add(h1);
		//enemies.add(g2);


		//BATTLE.battleStart(party, enemies);
		
		System.out.println("Link Health: " + Link.getHealth());
		System.out.println("Zelda Health: " + Zelda.getHealth());

		System.out.println("BATTLE START!");
		while(Zelda.getHealth() > 0 && Link.getHealth() > 0) {

			Zelda.attack(Link);
			Zelda.printStatus();
			Link.printStatus();
			Link.attack(Zelda);
			Zelda.printStatus();
			Link.printStatus();
			Zelda.useSpecial(Link);
			Zelda.printStatus();
			Link.printStatus();
			Link.useSpecial(Zelda);
		}
	}
}
