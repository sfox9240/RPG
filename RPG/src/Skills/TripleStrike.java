package Skills;
import Character.Character;
import Items.Intent;

/**
 * Created by piano_000 on 5/24/2015.
 */
public class TripleStrike extends Skill {

    public TripleStrike() {
        this.name = "Triple Strike";
        this.description = "Attack the opponent with three strikes";
        this.damage = 0;
        this.element = null;
        this.TPCost = 9;
        this.intent = Intent.HARM;
    }

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

            //Second Attack
            if(opponent.getHealth() > 0 && attacker.hitCalculator()) { //If the opponent isn't dead, do the second attack
                attacker.attack(opponent);
            } else {
                System.out.println(attacker.getName() + " missed their attack!");
            }

            //Third Attack
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
