package Skills;
import Character.Character;
import Game.Element;

/**
 * Created by piano_000 on 5/21/2015.
 */
public class DoubleStrike extends Skill {

    //Hero constructor
    public DoubleStrike() {
        this.name = "Double Strike";
        this.description = "Attack the opponent with two strikes";
        this.damage = 0;
        this.element = null;
        this.TPCost = 5;
    }

    /*
     * Allows the character to perform two attacks, back to back
     */
    @Override
    public void attack(Character attacker, Character opponent) {
        if((attacker.getTechniquePoints() - TPCost) >= 0) {
            Boolean hit = attacker.hitCalculator();
            if (hit) {
                //Attack lands
                System.out.println(attacker.getName() + " used " + name + "!");
                attacker.attack(opponent);
                if(opponent.getHealth() > 0) { //If the opponent isn't dead, do the second attack
                    attacker.attack(opponent);
                }
            } else {
                //Attack missed
                System.out.println(name + " missed their attack!");
            }
            attacker.setTechniquePoints(attacker.getTechniquePoints() - TPCost);
        } else {
            System.out.println("Not enough TP!");
        }
    }
}
