package Skills;
import Game.Element;
import Character.Character;
import Items.Intent;

/**
 * Created by piano_000 on 5/21/2015.
 *
 * The Skills manage themselves rather than being managed by the
 * character or enemy that uses them. The skill will handle all of its
 * own combat effects.
 */
public abstract class Skill {
    protected String name;
    protected String description;
    protected double damage;
    protected Element element;
    protected int TPCost;
    protected Intent intent;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getDamage() {
        return damage;
    }

    public Element getElement() { return element; }

    public int getTPCost() { return TPCost; }

    public Intent getIntent() {
        return intent;
    }

    /*
     * General attack type for single hit techniques
     * Check if the technique will lower TP below zero, if not then do
     * the technique.
     */

    /* TODO: Rename "attack" to "use". Also, attack should return to inform if there was enough TP to do the attack*/

    public void attack(Character attacker, Character opponent) {
        if((attacker.getTechniquePoints() - TPCost) >= 0) {
            Boolean hit = attacker.hitCalculator();
            System.out.println(attacker.getName() + " used " + name + "!");
            if (hit) {
                //Attack lands
                opponent.setHealth(opponent.getHealth() - this.damage);
                System.out.println(attacker.getName() + " dealt " + this.damage + " damage to " + opponent.getName());
                if (opponent.getHealth() <= 0) {
                    System.out.println(opponent.getName() + " has been felled!");
                }
            } else {
                //Attack missed
                System.out.println(attacker.getName() + " missed their attack!");
            }
            attacker.setTechniquePoints(attacker.getTechniquePoints() - TPCost);
        } else {
            System.out.println("Not enough TP!");
        }
    }
}
