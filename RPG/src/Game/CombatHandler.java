package Game;

import java.util.Vector;
import Character.Hero;
import Enemies.Enemy;

public class CombatHandler {
	/*
 	* There can only be one CombatHandler
 	* this will adhere to the singleton pattern
	 * Idea: Later establish a turn order vector, to allow for mixing of attack sequences from either side
	 * Example: hero attacks, then two enemies, then two heroes, then an enemy
	 */
	public CombatHandler() {}
	
	public void battleStart(Vector<Hero> heroes, Vector<Enemy> enemies) {
		//While both teams are alive
		System.out.println("BATTLE START!");
		while(heroesAlive(heroes) && enemiesAlive(enemies)) {
			//each of the heroes gets to attack
			for(int i = 0; i < heroes.size(); i++) {
				if(heroes.get(i).getHealth() > 0 && seekEnemy(enemies) != -1) {
					System.out.println(heroes.get(i).getName() + "'s turn!");
					//Fancy AI attack algorithm
					heroes.get(i).attack(enemies.get(seekEnemy(enemies))); //Attack the first enemy found to be alive
				}
			}
			//each other enemies gets to attack
			for(int j = 0; j < enemies.size(); j++) {
				if(enemies.get(j).getHealth() > 0 && seekHero(heroes) != -1) {
					System.out.println(enemies.get(j).getName() + "'s turn!");
					//Fancy AI attack algorithm
					enemies.get(j).attack(heroes.get(seekHero(heroes))); //Attack the first enemy found to be alive
				}
			}
		}
		victoryMessage(heroes);
	}
	
	/*
	 * Find a way to standardize these methods so they work for both Heroes and Enemies
	 */
	
	/*
	 * Checks the health of all members of the team
	 * If number of dead members are equal to the total team members
	 * the team is dead.
	 */
	protected boolean heroesAlive(Vector<Hero> team) {
		int deadMembers = 0;
		for(int i = 0; i < team.size(); i++) {
			if(team.get(i).getHealth() <= 0) {
				deadMembers++;
			}
		}
		if(deadMembers == team.size()){
			return false;
		} else {
			return true;
		}
	}
	
	protected boolean enemiesAlive(Vector<Enemy> team) {
		int deadMembers = 0;
		for(int i = 0; i < team.size(); i++) {
			if(team.get(i).getHealth() <= 0) {
				deadMembers++;
			}
		}
		
		if(deadMembers == team.size()){
			return false;
		} else {
			return true;
		}
	}
	
	//Check each opponent until you find a live one, return that one
	protected int seekEnemy(Vector<Enemy> opponents) {
		
		for(int i = 0; i < opponents.size(); i++) {
			if(opponents.get(i).getHealth() > 0) {
				return i;
			}
		}
		return -1; //No living opponents
	}
	
	protected int seekHero(Vector<Hero> opponents) {
		
		for(int i = 0; i < opponents.size(); i++) {
			if(opponents.get(i).getHealth() > 0) {
				return i;
			}
		}
		return -1; //No living opponents
	}
	
	protected void victoryMessage(Vector<Hero> heroes) {
		if(heroesAlive(heroes)) {
			System.out.println("The enemies are defeated! You Win!");
		} else {
			System.out.println("The party was defeated. GAME OVER!");
		}
	}
	
	
}
