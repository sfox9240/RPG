package Skills;
import Actor.Actor;
import Game.State;
import Game.TextHandler;
import Items.Intent;

import javax.xml.soap.Text;
import java.util.Vector;

/**
 * Created by piano_000 on 5/21/2015.
 */
public class DoubleStrike extends Skill {

    protected TextHandler out = TextHandler.getInstance();

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
    public double use(Actor attacker, Vector<Actor> opponents, int target) {
        Actor opponent = opponents.get(target);
        double threatBuilt = 0;
        if((attacker.getTechniquePoints() - TPCost) >= 0) {
            out.printToConsole(attacker.getName() + " used " + name + "!");
            threatBuilt = attacker.attack(opponent);
            if(opponent.getHealth() > 0.0 && opponent.getStatus().getState() != State.FAINTED) {
                threatBuilt = threatBuilt + attacker.attack((opponent));
            }
            attacker.subTechniquePoints(TPCost);
        } else {
            out.printToConsole("Not enough TP!");
            return -1;
        }
        return threatBuilt;
    }
}