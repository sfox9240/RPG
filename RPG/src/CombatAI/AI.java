package CombatAI;
import Actor.*;
import FightClasses.RandomGenerator;
import Items.Intent;
import Items.Item;
import Skills.Skill;

import java.util.Vector;

/**
 * Created by piano_000 on 5/26/2015.
 *
 * TODO: Create more robust AI that can handel status effects like poison and burning
 */
public class AI {
    protected Vector<Actor> heroes;
    protected Vector<Actor> enemies;
    int characterTurn;
    RandomGenerator generator;

    public AI(Vector<Actor> h, Vector<Actor> e) {
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

            switch(randomAttack) {
                case 1:
                    System.out.println("The AI chooses to use the weakest.");
                    enemies.get(characterTurn).attack(heroes.get(seekWeakest()));
                    break;
                case 2:
                    System.out.println("The AI chooses to use the strongest.");
                    enemies.get(characterTurn).attack(heroes.get(seekStrongest()));
                    break;
                case 3:
                    System.out.println("The AI chooses to use the first living.");
                    enemies.get(characterTurn).attack(heroes.get(seekFirstHero()));
                    break;
                case 4:
                    System.out.println("The AI Chooses to attack the biggest threat.");
                    enemies.get(characterTurn).attack(heroes.get(greatestThreat));
            }

        }
    }

    /*
    Find an ally that has less than 30% of their maximum tp. If there is an ally and a restore item exists, restore that ally's tp.
     */
    public Boolean restoreTP() {
        Boolean canRestore = false;
        Actor currentCharacter = enemies.get(characterTurn);
        int restoreIndex = 0;

        int i = 0;
        for(Item item: currentCharacter.getItems()) {
            if(item.getIntent() == Intent.RESTORE){
                canRestore = true;
                restoreIndex = i;
            }
            i++;
        }

        Actor lowest = null;

        if(canRestore) {
            //Find the weakest ally
            for (Actor enemy : enemies) {
                if (enemy.getTechniquePoints() < (enemy.getMaxTechniquePoints() * .30) && (lowest == null)) {
                    lowest = enemy;
                } else if (enemy.getTechniquePoints() < (enemy.getMaxTechniquePoints() * .30) && enemy.getTechniquePoints() < lowest.getTechniquePoints()) {
                    lowest = enemy;
                } else {
                    //NOTHING
                }
            }
            if(lowest != null) {
                enemies.get(characterTurn).getItems().get(restoreIndex).use(enemies.get(characterTurn), lowest); //Restore the enemy with the lowest tp that is below 30% of max
                return true;
            }
        }
        return false;
    }

    public Boolean healWounded() {
        Boolean useItem = false;
        Boolean useSkill = false;
        Actor currentCharacter = enemies.get(characterTurn);
        int usageIndex = 0;

        //First check if the character has a healing item
        int i = 0;
        for(Item item: currentCharacter.getItems()) {
            if(item.getIntent() == Intent.HEAL){
                useItem = true;
                usageIndex = i;
            }
            i++;
        }

        //If they don't have a healing item, check if they have a healing skill
        int j = 0;
        if(!useItem) {
            for(Skill skill: currentCharacter.getSkills()) {
                if(skill.getIntent() == Intent.HEAL) {
                    useSkill = true;
                    usageIndex = j;
                }
            j++;
            }
        }

        Actor weakest = null;

        //TODO: Change the healing algorithm to use the item that would heal the character the most, but go over their max health the least.
        if(useItem) {
            //Find the weakest ally
            for (Actor enemy : enemies) {
                if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && (weakest == null)) {
                    weakest = enemy;
                } else if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && enemy.getHealth() < weakest.getHealth()) {
                    weakest = enemy;
                } else {
                    //NOTHING heal = false;
                }
            }
            if(weakest != null) {
                enemies.get(characterTurn).getItems().get(usageIndex).use(enemies.get(characterTurn), weakest); //Heal the enemy with the lowest health that is below 30% of max
                return true;
            }
        }

        if(useSkill) {
            //Find the weakest ally
            for (Actor enemy : enemies) {
                if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && (weakest == null)) {
                    weakest = enemy;
                } else if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && enemy.getHealth() < weakest.getHealth()) {
                    weakest = enemy;
                } else {
                    //NOTHING heal = false;
                }
            }

            if(weakest != null) {
                enemies.get(characterTurn).getSkills().get(usageIndex).use(enemies.get(characterTurn), weakest); //Heal the enemy with the lowest health that is below 30% of max
                return true;
            }

        }
        return false;
    }

    /*
    --Find the weakest opponent--
    The first person found alive is "the weakest".
    After that if the amount of health left is lower than "the weakest", then this character is the new "the weakest"
    return the index of the weakest
     */
    protected int seekWeakest() {

        Actor weakest = null;
        int weakestIndex = 0;

        for (int i = 0; i < heroes.size(); i++) {
            double currentHeroHP = heroes.get(i).getHealth();
            double currentHeroMaxHP = heroes.get(i).getMaxHealth();

            if ((currentHeroHP > 0) && (weakest == null)) {
                weakest = heroes.get(i);
                weakestIndex = i;
            } else if ((currentHeroHP  > 0) && (currentHeroHP < (weakest.getHealth()))) {
                weakest = heroes.get(i);
                weakestIndex = i;
            } else {
                //NOTHING
            }
        }
        if (weakest != null) {
            return weakestIndex;
        } else {
            return -1; //No living opponents
        }
    }

    /*
    --Find the strongest opponent--
    The first person found alive is "the strongest".
    After that if the amount of health left is greater than "the strongest", then this character is the new "the strongest"
    return the index of the weakest
     */
    protected int seekStrongest() {

        Actor strongest = null;
        int strongestIndex = 0;

        for (int i = 0; i < heroes.size(); i++) {
            double currentHeroHP = heroes.get(i).getHealth();
            // double currentHeroMaxHP = heroes.get(i).getMaxHealth();

            if ((currentHeroHP > 0) && (strongest == null)) {
                strongest = heroes.get(i);
                strongestIndex = i;
            } else if ((currentHeroHP > 0) && (currentHeroHP > strongest.getHealth())) {
                strongest = heroes.get(i);
                strongestIndex = i;
            } else {
                //NOTHING
            }
        }
        if (strongest != null) {
            return strongestIndex;
        } else {
            return -1; //No living opponents
        }
    }

    /*
    Find the first living hero and use them
     */
    protected int seekFirstHero() {

        for(int i = 0; i < heroes.size(); i++) {
            if(heroes.get(i).getHealth() > 0) {
                return i;
            }
        }
        return -1; //No living opponents
    }
}
