package Skills;
import Character.Character;
import Items.Intent;

/**
 * Created by piano_000 on 5/21/2015.
 */
public class DoubleStrike extends Skill {

    public DoubleStrike() {
        this.name = "Double Strike";
        this.description = "Attack the opponent with two strikes";
        this.damage = 0;
        this.element = null;
        this.TPCost = 5;
        this.intent = Intent.HARM;
    }

    /*
     * Allows the character to perform two attacks, back to back
     */
    @Override
    public void attack(Character attacker, Character opponent) {
        if((attacker.getTechniquePoints() - TPCost) >= 0) {
            System.out.println(attacker.getName() + " used " + name + "!");
            //First Attack
            if(attacker.hitCalculator()) {
                attacker.attack(opponent);
            } else {
                System.out.println(attacker.getName() + " missed their attack!");
            }

            if(opponent.getHealth() > 0 && attacker.hitCalculator()) { //If the opponent isn't dead, do the second attack
                attacker.attack(opponent);
            } else {
                System.out.println(attacker.getName() + " missed their attack!");
            }
            attacker.setTechniquePoints(attacker.getTechniquePoints() - TPCost);
        } else {
            System.out.println("Not enough TP!");
        }
    }
}