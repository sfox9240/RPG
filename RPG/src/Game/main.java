package Game;

import java.util.Vector;

import Character.Hero;
import Enemies.*;
import FightClasses.*;
import Items.*;
import Skills.Fireball;
import Skills.Heal;
import Skills.TripleStrike;

public class main {

	public static void main(String[] args) {
		 /*
		 * This is just a testing ground for methods and different functionality
		 */
		
		CombatHandler BATTLE = new CombatHandler();
		Hero Link = new Hero("Link", new Knight());
		Hero Zelda = new Hero("Zelda", new Wizard());
		Hero Ganon = new Hero("Ganon", new BanditKing());
		
		System.out.println("Link Health: " + Link.getHealth());
		System.out.println("Link dmg mod: " + Link.getDamageMod());
		System.out.println("Zelda Health: " + Zelda.getHealth());
		System.out.println("Zelda dmg mod: " + Zelda.getDamageMod());
		
		Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
		Bow woodenBow = new Bow("Wooden Bow", "A nice wooden bow", true);

		Link.setWeapon(woodenSword);
		Zelda.setWeapon(woodenBow);
		Ganon.setWeapon(woodenSword);

		Vector<Hero> party = new Vector<Hero>();
		party.add(Link);
		party.add(Zelda);
		party.add(Ganon);
		
		Vector<Enemy> enemies = new Vector<Enemy>();
		
		Guard g1 = new Guard(); enemies.add(g1);
		Guard g2 = new Guard(); enemies.add(g2);
		Guard g3 = new Guard(); enemies.add(g3);
		//Goblin g1 = new Goblin();
		//Goblin g2 = new Goblin();
		//Hobgoblin h1 = new Hobgoblin();
		/*
		Club megaclub = new Club("Wooden Club", "A club", false);
		Lightning zap = new Lightning(new BaseAugment());
		megaclub.setAugment(zap);
		
		h1.setWeapon(megaclub);
		
		//enemies.add(g1);
		enemies.add(h1);
		//enemies.add(g2);

*/
		GreenHerb greenHerb = new GreenHerb();
		RedHerb redHerb = new RedHerb();
		Bomb bomb = new Bomb();
		g1.getItems().add(greenHerb);
		Link.getItems().add(greenHerb);
		Zelda.getItems().add(redHerb);
		Zelda.getItems().add(bomb);

		System.out.println("LINK INV:");
		Link.printInventory();
		System.out.println("ZELDA INV:");
		Zelda.printInventory();

		TripleStrike ts = new TripleStrike();
		Heal heals = new Heal();
		Fireball fb = new Fireball();
		Link.getSkills().add(ts);
		Link.getSkills().add(fb);
		Zelda.getSkills().add(heals);

		BATTLE.battleStart(party, enemies);
		/*System.out.println("BATTLE START!");
		while(Zelda.getHealth() > 0 && Link.getHealth() > 0) {

			Zelda.attack(Link);
			Zelda.printStatus();
			Link.printStatus();
			Link.attack(Zelda);
			Zelda.printStatus();
			if(Zelda.getHealth() < 10 && Zelda.getItems().contains(redHerb)) {
				Zelda.getItems().get(0).use(Zelda, Zelda);
			}
			if(Link.getHealth() < 10 && Link.getItems().contains(greenHerb)) {
				Link.getItems().get(0).use(Link, Link);
			}
			Link.printStatus();
			Zelda.useSpecial(Link);
			Zelda.printStatus();
			Link.printStatus();
			Link.useSpecial(Zelda);

			if(Zelda.getHealth() < 10 && !Zelda.getItems().contains(redHerb)) {
				Zelda.getItems().get(0).use(Zelda, Link);
			}
		}

		Link.printInventory();
		Zelda.printInventory();*/
	}
}
