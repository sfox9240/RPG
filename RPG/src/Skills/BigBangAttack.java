package Skills;
import Character.Character;
import Items.Intent;

/**
 * Created by piano_000 on 5/24/2015.
 */
public class BigBangAttack extends Skill{

    public BigBangAttack() {
        this.name = "Big Bang Attack";
        this.description = "Super Huge Explosion";
        this.damage = 50;
        this.element = element.FIRE;
        this.TPCost = 45;
        this.intent = Intent.HARM;
    }

    public void attack(Character attacker, Character opponent) {
        if((attacker.getTechniquePoints() - TPCost) >= 0) {
            Boolean hit = attacker.hitCalculator();
            if (hit) {
                //Attack lands
                System.out.println(attacker.getName() + " used " + name + "!");
                opponent.setHealth(opponent.getHealth() - this.damage);
                System.out.println(attacker.getName() + " dealt " + this.damage + " damage to " + opponent.getName());
                if (opponent.getHealth() <= 0) {
                    System.out.println(opponent.getName() + " has been felled!");
                }
            } else {
                //Attack missed
                System.out.println(opponent.getName() + " missed their attack!");
            }
            attacker.setTechniquePoints(attacker.getTechniquePoints() - TPCost);
        } else {
            System.out.println("Not enough TP!");
        }
    }
}
