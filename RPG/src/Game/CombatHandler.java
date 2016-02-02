package Game;

import java.util.Scanner;
import java.util.Vector;
import Actor.*;
import CombatAI.AI;
import CombatAI.AiAlpha;
import Items.Intent;
import Items.Item;
import Skills.Skill;

public class CombatHandler {
	/*
 	* There can only be one CombatHandler
 	* this will adhere to the singleton pattern
	 * Idea: Later establish a turn order vector, to allow for mixing of use sequences from either side
	 * Example: hero attacks, then two enemies, then two heroes, then an enemy
	 */

	private Scanner readAction = new Scanner(System.in);
	//TODO: Make heroes and enemies shared variables and create a clean up method to clean the class after a battle
	protected Vector<Actor> heroes = null;
	protected Vector<Actor> enemies = null;
	protected ThreatAnalyzer heroThreat = null;
	protected TextHandler out = null;

	public CombatHandler() {
		out = TextHandler.getInstance();
		System.out.println("Handler from CH: " + System.identityHashCode(out));
	}

	/*
	BattleStart manages turn order of the encounter the enemy AI and the player commands control
	what each party chooses to do during each of those turns
	 */
	public void battleStart(Vector<Actor> heroes, Vector<Actor> enemies) {
		this.heroes = heroes;
		this.enemies = enemies;
		AI enemyAI = new AiAlpha(heroes, enemies);
		heroThreat = new ThreatAnalyzer(heroes);

		//While both teams are alive
		System.out.println("BATTLE START!");
		while(teamAlive(heroes) && teamAlive(enemies)) {
			//each of the heroes gets to use
			for(int i = 0; i < heroes.size(); i++) {
				heroes.get(i).takeStatusDamage();
				if(heroes.get(i).getHealth() > 0 && seekOpponent(enemies) != -1) {
					System.out.println(heroes.get(i).getName() + "'s turn!");
					playerAction(i);
				}
			}
			heroThreat.determineThreat();
			//each other enemies gets to use
			for (int j = 0; j < enemies.size(); j++) {
				enemies.get(j).takeStatusDamage();
				if (enemies.get(j).getHealth() > 0 && seekOpponent(heroes) != -1) {
					System.out.println(enemies.get(j).getName() + "'s turn!");
					enemyAI.takeTurn(j, heroThreat.getGreatestSource());
				}
			}
			//Decrement illness and status durations
			//decrementStatusDurations();
		}
		victoryMessage();
		clearBattleField();
	}

	/*
	int heroTurn: the index of the currently active hero

	 */
	protected void playerAction(int heroTurn) {
		Boolean validResponse = false;
		String action1;
		if(heroes.get(heroTurn).getStatus().getState() == State.GUARDING) { //New turn, no longer guarding
			heroes.get(heroTurn).getStatus().setState(State.NORMAL, 0);
		}

		while(!validResponse) {
			printBattleOptions();
			action1 = readAction.nextLine();

			switch (action1) {
				case "1": //Attack
					validResponse = basicAttack(heroTurn);
					break;
				case "2":
					validResponse = true;
					heroes.get(heroTurn).getStatus().setState(State.GUARDING, 1);
					System.out.println(heroes.get(heroTurn).getName() + " is guarding!");
					break;
				case "3":
					validResponse = useItem(heroTurn);
					break;
				case "4":
					validResponse = useSkill(heroTurn);
					break;
				case "5":
					//TODO: Implement Run and, you know, a place to run too...
					out.printToConsole("Run is not yet implemented.");
					break;
				default:
					break;
			}
		}
	}

	/*
	Do a basic use for a character against a character
	 */
	protected Boolean basicAttack(int heroTurn) {
		int target = selectTarget();

		if(target <= -1) {
			return false;
		}

		Actor attacker = heroes.get(heroTurn);
		Actor opponent = enemies.get(target);

		attacker.attack(opponent);
		return true;
	}

	/*
	Select a target from enemies
	Success: returns the value of the enemy in the enemies vector
	Cancel: returns -1
	Failure: returns -2

	 */

	/*
	TODO: Add actual error handling
	 */
	protected int selectTarget() {
		printEnemies();
		String action;
		Boolean validAttackResponse = false;
		while(!validAttackResponse) {
			action = readAction.nextLine();
			if(action.length() > 0 && isValid(action, "Integer")) {
				int action2val = Integer.parseInt(action);

				if (action2val <= enemies.size() && action2val > 0) {
					return (action2val - 1);
				}

				//Back command
				if ((action2val) == 0) {
					validAttackResponse = true;
				}
			}
		}
		return -2; // Error
	}

	protected Boolean useItem(int heroTurn) {
		String action;
		Boolean validItemResponse = false;
		Boolean validResponse = false;
		while(!validItemResponse) {
			printItems(heroTurn);
			action = readAction.nextLine();
			if(action.length() > 0 && isValid(action, "Integer")) {
				int actionVal = Integer.parseInt(action);

				//Back command
				if ((actionVal) == 0) {
					validItemResponse = true;
				}

				if (actionVal <= heroes.get(heroTurn).getItems().size() && actionVal > 0) {
					validItemResponse = true;

					int target = selectItemTarget(heroTurn, (actionVal - 1));
					if (heroes.get(heroTurn).getItems().get((actionVal - 1)).getIntent() == Intent.HEAL) { //HEAL Party Member
						heroes.get(heroTurn).getItems().get((actionVal - 1)).use(heroes.get(heroTurn), heroes, (target - 1));
						//heroes.get(heroTurn).useItem(heroes.get(target - 1), (actionVal- 1));
						validResponse = true;
					} else if (heroes.get(heroTurn).getItems().get((actionVal - 1)).getIntent() == Intent.HARM) { //Harm Enemy
						heroes.get(heroTurn).getItems().get((actionVal - 1)).use(heroes.get(heroTurn), enemies, (target - 1));
						//heroes.get(heroTurn).useItem(enemies.get(target - 1), (actionVal - 1));
						validResponse = true;
					} else { //Cannot Equip weapon during combat
						System.out.println("Cannot equip weapons during combat");
					}
				}
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
			if(action.length() > 0 && isValid(action, "Integer")) {
				int actionVal = Integer.parseInt(action);

				//Back command
				if ((actionVal) == 0) {
					return actionVal;
				}

				//Determine what is a valid response
				if (item.getIntent() == Intent.HARM) {
					if (actionVal <= enemies.size() && actionVal > 0) {
						return actionVal;
					}
				}

				if (item.getIntent() == Intent.HEAL) {
					if (actionVal <= heroes.size() && actionVal > 0) {
						return actionVal;
					}
				}
				System.out.println("Invalid Selection!");
			}
		}
		return -2; // Error Code
	}

	protected Boolean useSkill(int heroTurn) {
		String action;
		Boolean validItemResponse = false;
		Boolean validResponse = false;
		while(!validItemResponse) { //Until the user input a valid item index...
			printSkills(heroTurn); //Print the list of skills and read in the user's selected skill
			action = readAction.nextLine();
			if(action.length() > 0 && isValid(action, "Integer")) { //Confirm the input is actually an integer...
				int actionVal = Integer.parseInt(action);
				//Back command
				if (actionVal == 0) { //and if it's zero then go back...
					validItemResponse = true;
				}  else if (actionVal <= heroes.get(heroTurn).getSkills().size() && actionVal > 0) { //if its a valid skill index let's do some work...
					validItemResponse = true;
					int target = selectSkillTarget(heroTurn, (actionVal - 1)); //Select the target of the skill

					//Back command
					if(target == -1) { //If the target is -1, (meaning zero was selected) then go back to the primary menu
						return false;
					}
					if (heroes.get(heroTurn).getSkills().get((actionVal - 1)).getIntent() == Intent.HEAL) { //HEAL Party Member
						double skillResponse = heroes.get(heroTurn).getSkills().get((actionVal - 1)).use(heroes.get(heroTurn), heroes, (target - 1));
						if(skillResponse == -1) { //Not enough TP, return to playerAction
							validResponse = false;
						} else {
							heroThreat.addThreat(heroTurn, skillResponse);
							validResponse = true;
						}
					} else if (heroes.get(heroTurn).getSkills().get((actionVal - 1)).getIntent() == Intent.HARM) { //Harm Enemy
						double  skillResponse = heroes.get(heroTurn).getSkills().get((actionVal - 1)).use(heroes.get(heroTurn), enemies, (target - 1));
						if(skillResponse == -1) { //Not enough TP, return to playerAction
							validResponse = false;
						} else {
							heroThreat.addThreat(heroTurn, skillResponse);
							validResponse = true;
						}
					} else { //Cannot Equip weapon during combat
						System.out.println("Cannot equip weapons during combat");
					}
				}
			}
		}
		return validResponse;
	}

	/*
	Helper function for selectItem
	Determines which type of character the selected item can be u,sed on and outputs the relative character list
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
			if(action.length() > 0 && isValid(action, "Integer")) {
				int actionVal = Integer.parseInt(action);

				//Back command
				if ((actionVal) == 0) {
					return actionVal;
				}

				//Determine what is a valid response
				if (skill.getIntent() == Intent.HARM) {
					if (actionVal <= enemies.size() && actionVal > 0) {
						return actionVal;
					}
				}

				if (skill.getIntent() == Intent.HEAL) {
					if (actionVal <= heroes.size() && actionVal > 0) {
						return actionVal;
					}
				}
				System.out.println("Invalid Selection!");
			}
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
			System.out.println((i + 1) + ") " + enemies.get(i).getName() + " HP: " + enemies.get(i).getHealth()
					+ " Status: " + enemies.get(i).getStatus().getState() + " Illness: " + enemies.get(i).getStatus().getIllness()
					+ "(" + enemies.get(i).getStatus().getIllnessDuration() + ")");
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

	protected void decrementStatusDurations() {
		for(int i = 0; i < heroes.size(); i++) {
			if(heroes.get(i).getStatus().getState() != State.NORMAL || heroes.get(i).getStatus().getState() != State.FAINTED) {
				heroes.get(i).getStatus().elapseStete(1);
				if(heroes.get(i).getStatus().getStateDuration() <= 0) {
					heroes.get(i).getStatus().setState(State.NORMAL, -1);
				}
			}
			if(heroes.get(i).getStatus().getIllness() != Illness.NONE) {
				heroes.get(i).getStatus().elapseIllness(1);
				if(heroes.get(i).getStatus().getIllnessDuration() <= 0) {
					heroes.get(i).getStatus().setIllness(Illness.NONE, -1);
				}
			}
		}
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getStatus().getState() != State.NORMAL || enemies.get(i).getStatus().getState() != State.FAINTED) {
				enemies.get(i).getStatus().elapseStete(1);
				if(enemies.get(i).getStatus().getStateDuration() <= 0) {
					enemies.get(i).getStatus().setState(State.NORMAL, -1);
				}
			}
			if(enemies.get(i).getStatus().getIllness() != Illness.NONE) {
				enemies.get(i).getStatus().elapseIllness(1);
				if(enemies.get(i).getStatus().getIllnessDuration() <= 0) {
					enemies.get(i).getStatus().setIllness(Illness.NONE, -1);
				}
			}
		}
	}

	/*
	 * Checks the health of all members of the team
	 * If number of dead members are equal to the total team members
	 * the team is dead.
	 */
	protected boolean teamAlive(Vector<Actor> team) {
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
	protected int seekOpponent(Vector<Actor> team) {
		for(int i = 0; i < team.size(); i++) {
			if(team.get(i).getHealth() > 0) {
				return i;
			}
		}
		return -1; //No living opponents
	}
	
	protected void victoryMessage() {
		if(teamAlive(heroes)) {
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

	/*
	Helper function:
	Checks if a string is a valid Integer or alphanumeric string
	 */
	public static Boolean isValid(String str, String clazz) {
		if(clazz.equals("Integer")) {
			return str.matches("(\\+|-)?[0-9]+");
		} else if(clazz.equals("String")) {
			return str.matches("[A-Za-z]+");
		} else {
			return false;
		}
	}
}
