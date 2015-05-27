package CombatAI;
import Character.*;
import Enemies.Enemy;
import Items.Intent;
import Items.Item;
import Skills.Skill;

import java.util.Vector;

/**
 * Created by piano_000 on 5/26/2015.
 */
public class AI {
    protected Vector<Hero> heroes;
    protected Vector<Enemy> enemies;
    int characterTurn;

    public AI(Vector<Hero> h, Vector<Enemy> e) {
        heroes = h;
        enemies = e;
    }

    public void takeTurn(int characterTurn) {
        this.characterTurn = characterTurn;
        Boolean heal = healWounded();
        if(!heal) {
            System.out.println("The AI attempts to attack.");
            enemies.get(characterTurn).attack(heroes.get(seekHero()));
        }
    }
/*
    public Boolean healWounded2() {
        Enemy weakest = null;
        Boolean heal = true;

        for (Enemy enemy : enemies) {
            if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && (weakest == null)) {
                weakest = enemy;
            } else if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && enemy.getHealth() < weakest.getHealth()) {
                weakest = enemy;
            } else {
                heal = false;
            }
        }

        Enemy currentChar = enemies.get(characterTurn);

        for(Item item: currentChar.getItems()) {
            if

        }
    }
    */

    public Boolean healWounded() {
        Boolean useItem = false;
        Boolean useSkill = false;
        Enemy currentCharacter = enemies.get(characterTurn);
        int usageIndex = 0;

        System.out.println("The AI attempts to heal");
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
        //TODO: Fix this
        Enemy weakest = null; //= enemies.get(0);

        //TODO: Change the healing algorithm to use the item that would heal the character the most, but go over their max health the least.
        if(useItem) {
            //Find the weakest ally
            for (Enemy enemy : enemies) {
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
            for (Enemy enemy : enemies) {
                if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && (weakest == null)) {
                    weakest = enemy;
                } else if (enemy.getHealth() < (enemy.getMaxHealth() * .30) && enemy.getHealth() < weakest.getHealth()) {
                    weakest = enemy;
                } else {
                    //NOTHING heal = false;
                }
            }

            if(weakest != null) {
                enemies.get(characterTurn).getSkills().get(usageIndex).attack(enemies.get(characterTurn), weakest); //Heal the enemy with the lowest health that is below 30% of max
                return true;
            }

        }
        return false;
    }

    protected int seekHero() {

        for(int i = 0; i < heroes.size(); i++) {
            if(heroes.get(i).getHealth() > 0) {
                return i;
            }
        }
        return -1; //No living opponents
    }
}
