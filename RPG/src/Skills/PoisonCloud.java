package Skills;

import Actor.Actor;
import Game.Illness;
import Items.Intent;

import java.util.Vector;

/**
 * Created by piano_000 on 1/30/2016.
 */
public class PoisonCloud extends Skill {
    public PoisonCloud() {
        this.name = "Poison Cloud";
        this.description = "Blasts an enmey with a cloud of poison.";
        this.damage = 0;
        this.element = element.POISON;
        this.TPCost = 8;
        this.intent = Intent.HARM;
        this.duration = 3;
    }

    public double use(Actor attacker, Vector<Actor> opponents, int target) {
        double threatBuilt = 0;
        Actor opponent = opponents.get(target);
        if ((attacker.getTechniquePoints() - TPCost) >= 0) {

            out.printToConsole(attacker.getName() + " used " + name + "!");
            opponent.addHealth(damage);
            out.printToConsole(attacker.getName() + " has poisoned " + opponent.getName());
            opponent.getStatus().setIllness(Illness.POISON, duration);
            threatBuilt = this.damage;
            attacker.subTechniquePoints(TPCost);
        } else {
            out.printToConsole("Not enough TP!");
        }
        return threatBuilt;
    }
}
