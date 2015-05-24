package Game;

import java.util.Scanner;
import java.util.Vector;
import Character.Hero;
import Enemies.Enemy;
import Items.Intent;
import Items.Item;
import Skills.Skill;

public class CombatHandler {
	/*
 	* There can only be one CombatHandler
 	* this will adhere to the singleton pattern
	 * Idea: Later establish a turn order vector, to allow for mixing of attack sequences from either side
	 * Example: hero attacks, then two enemies, then two heroes, then an enemy
	 */

	private Scanner readAction = new Scanner(System.in);
	//TODO: Make heroes and enemies shared variables and create a clean up method to clean the class after a battle
	protected Vector<Hero> heroes = null;
	protected Vector<Enemy> enemies = null;

	public CombatHandler() {}

	/*
	BattleStart manages turn order of the encounter the enemy AI and the player commands control
	what each party chooses to do during each of those turns
	 */
	public void battleStart(Vector<Hero> heroes, Vector<Enemy> enemies) {
		this.heroes = heroes;
		this.enemies = enemies;
		//While both teams are alive
		System.out.println("BATTLE START!");
		while(heroesAlive() && enemiesAlive()) {
			//each of the heroes gets to attack
			for(int i = 0; i < heroes.size(); i++) {
				if(heroes.get(i).getHealth() > 0 && seekEnemy() != -1) {
					System.out.println(heroes.get(i).getName() + "'s turn!");
					//Fancy AI attack algorithm
					playerAction(i);
				}
			}
			//each other enemies gets to attack
			for (Enemy enemy : enemies) {
				if (enemy.getHealth() > 0 && seekHero() != -1) {
					System.out.println(enemy.getName() + "'s turn!");
					//Fancy AI attack algorithm
					enemy.attack(heroes.get(seekHero())); //Attack the first enemy found to be alive
				}
			}
		}
		victoryMessage();
		clearBattleField();
	}

	/*

	 */
	protected void playerAction(int heroTurn) {
		Boolean validResponse = false;
		String action1;

		while(!validResponse) {
			printBattleOptions();
			action1 = readAction.nextLine();

			switch (action1) {
				case "1": //Attack
					validResponse = selectAttackTarget(heroTurn);
					break;
				case "2":
					//UNDER CONSTRUCTION TODO:Implement blocking
					System.out.println("This doesn't do anything yet...");
					break;
				case "3":
					validResponse = selectItem(heroTurn);
					break;
				case "4":
					validResponse = selectSkill(heroTurn);
					break;
				case "5":
					//TODO: Implement Run
			}
		}
	}

	/*
	TODO: Add actual error handling
	 */
	protected Boolean selectAttackTarget(int heroTurn) {
		printEnemies();
		String action;
		Boolean validAttackResponse = false;
		Boolean validResponse = false;
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
	protected Boolean selectItem(int heroTurn) {
		String action;
		Boolean validItemResponse = false;
		Boolean validResponse = false;
		while(!validItemResponse) {
			printItems(heroTurn);
			action = readAction.nextLine();
			int actionVal = Integer.parseInt(action);

			if(actionVal <= heroes.get(heroTurn).getItems().size() && actionVal > 0) {
				validItemResponse = true;

				int target = selectItemTarget(heroTurn, (actionVal - 1));
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
	protected int selectItemTarget(int heroTurn, int itemIndex) {
		Item item = heroes.get(heroTurn).getItems().get(itemIndex);

		String action;
		Boolean validResponse = false;
		while(!validResponse) {
			if(item.getIntent() == Intent.EQUIP) { //Cannot use item
				return -10;
			} else if(item.getIntent() == Intent.HARM) { //Only show enemies for harming
				printEnemies();
			} else { //Only show allies for healing
				printHeroes();
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
	TODO: Add actual error handling
	 */
	protected Boolean selectSkill(int heroTurn) {
		String action;
		Boolean validItemResponse = false;
		Boolean validResponse = false;
		while(!validItemResponse) {
			printSkills(heroTurn);
			action = readAction.nextLine();
			int actionVal = Integer.parseInt(action);

			if (actionVal <= heroes.get(heroTurn).getSkills().size() && actionVal > 0) {
				validItemResponse = true;
				int target = selectSkillTarget(heroTurn, (actionVal - 1));
				if(heroes.get(heroTurn).getSkills().get((actionVal - 1)).getIntent() == Intent.HEAL) { //HEAL Party Member
					heroes.get(heroTurn).getSkills().get((actionVal - 1)).attack(heroes.get(heroTurn), heroes.get(target - 1));
					validResponse = true;
				} else if(heroes.get(heroTurn).getSkills().get((actionVal - 1)).getIntent() == Intent.HARM) { //Harm Enemy
					heroes.get(heroTurn).getSkills().get((actionVal - 1)).attack(heroes.get(heroTurn), enemies.get(target - 1));
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
	protected int selectSkillTarget(int heroTurn, int skillIndex) {
		Skill skill = heroes.get(heroTurn).getSkills().get(skillIndex);

		String action;
		Boolean validResponse = false;
		while(!validResponse) {
			if(skill.getIntent() == Intent.HARM) {
				printEnemies();
			} else { //Only show allies for healing
				printHeroes();
			}
			action = readAction.nextLine();
			int actionVal = Integer.parseInt(action);

			//Determine what is a valid response
			if(skill.getIntent() == Intent.HARM) {
				if (actionVal <= enemies.size() && actionVal > 0) {
					validResponse = true;
					return actionVal;
				}
			}

			if(skill.getIntent() == Intent.HEAL) {
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
		for(int i = 0; i < heroes.size(); i++) {
			heroes.get(i).printStatus();
		}
		System.out.println("1) Attack   2) Guard   3) Items\n4) Skills   5) Run\nSelect an action: ");
	}

	/*
	Prints the list of enemies
	 */
	protected void printEnemies() {
		for(int i = 0; i < enemies.size(); i++) {
			System.out.println((i + 1) + ") " + enemies.get(i).getName() + " HP: " + enemies.get(i).getHealth());
		}
		System.out.println("Select an action: ");
	}

	protected void printHeroes() {
		for(int i = 0; i < heroes.size(); i++) {
			System.out.println((i + 1) + ") " + heroes.get(i).getName() + " HP: " + heroes.get(i).getHealth() + " TP: " + heroes.get(i).getTechniquePoints());
		}
		System.out.println("Select an action: ");
	}

	protected void printItems(int heroTurn) {
		for(int i =0; i < heroes.get(heroTurn).getItems().size(); i++) {
			System.out.println((i +1) + ") " + heroes.get(heroTurn).getItems().get(i).getName() + " --- " + heroes.get(heroTurn).getItems().get(i).getDescription());
		}
		System.out.println("Select an action: ");
	}

	protected void printSkills(int heroTurn) {
		for(int i =0; i < heroes.get(heroTurn).getSkills().size(); i++) {
			System.out.println((i +1) + ") " + heroes.get(heroTurn).getSkills().get(i).getName() + " --- TP: " + heroes.get(heroTurn).getSkills().get(i).getTPCost() + " --- " + heroes.get(heroTurn).getSkills().get(i).getDescription());
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
	protected boolean heroesAlive() {
		int deadMembers = 0;
		for(int i = 0; i < heroes.size(); i++) {
			if(heroes.get(i).getHealth() <= 0) {
				deadMembers++;
			}
		}
		if(deadMembers == heroes.size()){
			return false;
		} else {
			return true;
		}
	}
	
	protected boolean enemiesAlive() {
		int deadMembers = 0;
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getHealth() <= 0) {
				deadMembers++;
			}
		}
		
		if(deadMembers == enemies.size()){
			return false;
		} else {
			return true;
		}
	}
	
	//Check each opponent until you find a live one, return that one
	protected int seekEnemy() {
		
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getHealth() > 0) {
				return i;
			}
		}
		return -1; //No living opponents
	}
	
	protected int seekHero() {
		
		for(int i = 0; i < heroes.size(); i++) {
			if(heroes.get(i).getHealth() > 0) {
				return i;
			}
		}
		return -1; //No living opponents
	}
	
	protected void victoryMessage() {
		if(heroesAlive()) {
			System.out.println("The enemies are defeated! You Win!");
		} else {
			System.out.println("The party was defeated. GAME OVER!");
		}
	}

	/*
	Clear the battlefield for the next battle
	 */
	protected void clearBattleField() {
		this.heroes = null;
		this.enemies = null;
	}
	
	
}
