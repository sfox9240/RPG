package CombatAI;

import Actor.Actor;
import FightClasses.RandomGenerator;
import Items.Intent;
import Skills.Skill;

import java.util.Vector;

/**
 * Created by piano_000 on 2/1/2016.
 *
 *
 * TODO: Need to expand combat logic to:
 * Determine what special attack to use
 *   Use AOEs as priority over non-AOEs or maybe on a cool-down?
 *   Random chance to try to use a special instead of a normal attack
 *  More sophisticated method of determining who should be attacked
 */

public class AiAlpha extends AI {

    public AiAlpha(Vector<Actor> h, Vector<Actor> e) {
        heroes = h;
        enemies = e;
        generator = new RandomGenerator();
    }

    public void takeTurn(int characterTurn, int greatestThreat) {
        this.characterTurn = characterTurn;
        Boolean heal = healWounded();
        Boolean restore = true;
        if(!heal) {
            restore = restoreTP();
        }

        if(!heal || !restore) {
            //TODO: Replace this with more logic so attacking is a little less random
            int ATTACKTYPES = 4; //This is the total number of use methods
            int randomAttack = generator.getNumberBetween(1, ATTACKTYPES);
            int target = seekFirstHero();

            switch(randomAttack) {
                case 1:
                    //out.printToConsole("The AI chooses to attack the weakest.");
                    target = seekWeakest();
                    break;
                case 2:
                    //out.printToConsole("The AI chooses to attack the strongest.");
                    target = seekStrongest();
                    break;
                case 3:
                    //out.printToConsole("The AI chooses to attack the first living.");
                    target = seekFirstHero();
                    break;
                case 4:
                    //out.printToConsole("The AI Chooses to attack the biggest threat.");
                    target = greatestThreat;
            }
            int doSkill = generator.getNumberBetween(1,4); //25% just cause
            switch (doSkill) {
                case 1:
                    int useSkill = chooseAttackSkill();
                    if(useSkill != -1) {
                        enemies.get(characterTurn).getSkills().get(useSkill).use(enemies.get(characterTurn), heroes, target);
                    } else {
                        enemies.get(characterTurn).attack(heroes.get(target));
                    }
                    break;
                default:
                    enemies.get(characterTurn).attack(heroes.get(target));
                    break;

            }

        }
    }


    public int chooseAttackSkill() {
        Actor currentAttacker = enemies.get(characterTurn);
        for(int i = 0; i < enemies.get(characterTurn).getSkills().size(); i++) {
            Skill currentSkill = enemies.get(characterTurn).getSkills().get(i);
            if(currentSkill.getIntent() == Intent.HARM) {
                if(currentAttacker.getTechniquePoints() >= currentSkill.getTPCost()) {
                    return i; //TODO: Figure something out later...
                }
            }
        }
        return -1; //No skill found
    }
}
