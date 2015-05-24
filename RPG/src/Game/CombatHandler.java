package Game;

import java.util.Scanner;
import java.util.Vector;
import Character.Hero;
import Enemies.Enemy;
import Items.Intent;
import Items.Item;

public class CombatHandler {
	/*
 	* There can only be one CombatHandler
 	* this will adhere to the singleton pattern
	 * Idea: Later establish a turn order vector, to allow for mixing of attack sequences from either side
	 * Example: hero attacks, then two enemies, then two heroes, then an enemy
	 */

	private Scanner readAction = new Scanner(System.in);
	//TODO: Make heroes and enemies shared variables and create a clean up method to clean the class after a battle
	//protected Vector<Hero> heroes = null;
	//protected Vector<Enemy> enemies = null;

	public CombatHandler() {}

	/*
	BattleStart manages turn order of the encounter the enemy AI and the player commands control
	what each party chooses to do during each of those turns
	 */
	public void battleStart(Vector<Hero> heroes, Vector<Enemy> enemies) {
		//While both teams are alive
		System.out.println("BATTLE START!");
		while(heroesAlive(heroes) && enemiesAlive(enemies)) {
			//each of the heroes gets to attack
			for(int i = 0; i < heroes.size(); i++) {
				if(heroes.get(i).getHealth() > 0 && seekEnemy(enemies) != -1) {
					System.out.println(heroes.get(i).getName() + "'s turn!");
					//Fancy AI attack algorithm
					playerAction(heroes, i, enemies);
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

	 */
	protected void playerAction(Vector<Hero> heroes, int heroTurn, Vector<Enemy> enemies) {
		Boolean validResponse = false;
		String action1 = "";

		while(!validResponse) {
			printBattleOptions();
			action1 = readAction.nextLine();

			switch (action1) {
				case "1": //Attack
					validResponse = selectAttackTarget(heroes, heroTurn, enemies, validResponse);
					break;
				case "2":
					//UNDER CONSTRUCTION TODO:Implement blocking
					System.out.println("This doesn't do anything yet...");
					break;
				case "3":
					validResponse = selectItem(heroes, heroTurn, enemies, validResponse);
					break;
				case "4":
					//TODO: Implement Skills
					break;
				case "5":
					//TODO: Implement Run
			}
		}
	}

	/*
	TODO: Add actual error handling
	 */
	protected Boolean selectAttackTarget(Vector<Hero> heroes, int heroTurn, Vector<Enemy> enemies, Boolean validResponse) {
		printEnemies(enemies);
		String action;
		Boolean validAttackResponse = false;
		while(!validAttackResponse) {
			action = readAction.nextLine();
			int action2val = Integer.parseInt(action);

			if(action2val <= enemies.size() && action2val > 0) {
				validAttackResponse = true;
				heroes.get(heroTurn).attack(enemies.get((action2val - 1)));
				validResponse = true;
			}

			//Back command
			if((action2val) == 0) {
				validAttackResponse = true;
			}
		}
		return validResponse;
	}

	/*
	TODO: Add actual error handling
	 */
	protected Boolean selectItem(Vector<Hero> heroes, int heroTurn, Vector<Enemy> enemies, Boolean validResponse) {
		String action;
		Boolean validItemResponse = false;
		while(!validItemResponse) {
			printItems(heroes, heroTurn);
			action = readAction.nextLine();
			int actionVal = Integer.parseInt(action);

			if(actionVal <= heroes.get(heroTurn).getItems().size() && actionVal > 0) {
				validItemResponse = true;

				int target = selectItemTarget(heroes, heroTurn, enemies, (actionVal - 1));
				if(heroes.get(heroTurn).getItems().get((actionVal - 1)).getIntent() == Intent.HEAL) { //HEAL Party Member
					heroes.get(heroTurn).getItems().get((actionVal - 1)).use(heroes.get(heroTurn), heroes.get(target - 1));
					validResponse = true;
				} else if(heroes.get(heroTurn).getItems().get((actionVal - 1)).getIntent() == Intent.HARM) { //Harm Enemy
					heroes.get(heroTurn).getItems().get((actionVal - 1)).use(heroes.get(heroTurn), enemies.get(target - 1));
					validResponse = true;
				} else { //Cannot Equip weapon during combat
					System.out.println("Cannot equip weapons during combat");
				}
			}

			//Back command
			if((actionVal) == 0) {
				validItemResponse = true;
			}
		}
		return validResponse;
	}

	/*
	Helper function for selectItem
	Determines which type of character the selected item can be used on and outputs the relative character list
	and returns the index of the target
	 */
	protected int selectItemTarget(Vector<Hero> heroes, int heroTurn, Vector<Enemy> enemies, int itemIndex) {
		Item item = heroes.get(heroTurn).getItems().get(itemIndex);


		String action;
		Boolean validResponse = false;
		while(!validResponse) {
			if(item.getIntent() == Intent.EQUIP) { //Cannot use item
				return -10;
			} else if(item.getIntent() == Intent.HARM) { //Only show enemies for harming
				printEnemies(enemies);
			} else { //Only show allies for healing
				printHeroes(heroes);
			}
			action = readAction.nextLine();
			int actionVal = Integer.parseInt(action);

			//Determine what is a valid response
			if(item.getIntent() == Intent.HARM) {
				if (actionVal <= enemies.size() && actionVal > 0) {
					validResponse = true;
					return actionVal;
				}
			}

			if(item.getIntent() == Intent.HEAL) {
				if (actionVal <= heroes.size() && actionVal > 0) {
					validResponse = true;
					return actionVal;
				}
			}

			//Back command
			if((actionVal) == 0) {
				validResponse = true;
				return actionVal;
			}
			System.out.println("Invalid Selection!");
		}
		return -2; // Error Code
	}

	/*
	A helper function for the playAction method. Simply prints the first list of commands
	the player can choose from.
	 */
	protected void printBattleOptions() {
		System.out.println("1) Attack   2) Guard   3) Items\n4) Skills   5) Run\nSelect an action: ");
	}

	/*
	Prints the list of enemies
	 */
	protected void printEnemies(Vector<Enemy> enemies) {
		for(int i = 0; i < enemies.size(); i++) {
			System.out.println((i + 1) + ") " + enemies.get(i).getName() + " HP: " + enemies.get(i).getHealth());
		}
		System.out.println("Select an action: ");
	}

	protected void printHeroes(Vector<Hero> heroes) {
		for(int i = 0; i < heroes.size(); i++) {
			System.out.println((i + 1) + ") " + heroes.get(i).getName() + " HP: " + heroes.get(i).getHealth());
		}
		System.out.println("Select an action: ");
	}

	protected void printItems(Vector<Hero> heroes, int heroTurn) {
		for(int i =0; i < heroes.get(heroTurn).getItems().size(); i++) {
			System.out.println((i +1) + ") " + heroes.get(heroTurn).getItems().get(i).getName() + "---" + heroes.get(heroTurn).getItems().get(i).getDescription());
		}
		System.out.println("Select an action: ");
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
