package Skills;

import Actor.Actor;
import Items.Intent;
import Actor.*;
/**
 * Created by piano_000 on 5/24/2015.
 */
public class Heal extends Skill {

    public Heal() {
        this.name = "Heal";
        this.description = "Heals an ally for 20 HP";
        this.damage = 20; // TODO: I should probably rename damage to something else.
        this.element = null;
        this.TPCost = 15;
        this.intent = Intent.HEAL;
    }

    /*
    You cannot miss a heal
     */
    public double use(Actor attacker, Actor opponent) {
        double threatBuilt = 0;
        if ((attacker.getTechniquePoints() - TPCost) >= 0) {

            System.out.println(attacker.getName() + " used " + name + "!");
            opponent.setHealth(opponent.getHealth() + damage);
            System.out.println(attacker.getName() + " healed " + opponent.getName() + " damage to " + damage);
            threatBuilt = this.damage;
            attacker.setTechniquePoints(attacker.getTechniquePoints() - TPCost);
        } else {
            System.out.println("Not enough TP!");
        }
        return threatBuilt;
    }
}
