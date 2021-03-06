package Skills;
import Game.Element;
import Actor.Actor;
import Game.State;
import Game.Status;
import Game.TextHandler;
import Items.Intent;

import java.util.Vector;

/**
 * Created by piano_000 on 5/21/2015.
 *
 * The Skills manage themselves rather than being managed by the
 * character or enemy that uses them. The skill will handle all of its
 * own combat effects. Intent is used by the CombatHandler to determine if the
 * skill affects the party or the enemies. AOE is used to determine if the skill
 * effects a single actor or the full party
 */
public abstract class Skill {
    protected String name;
    protected String description;
    protected double damage;
    protected Element element;
    protected int TPCost;
    protected Intent intent;
    protected TextHandler out = TextHandler.getInstance();
    protected Boolean AOE = false;
    protected int duration = 0;

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

    public Boolean isAOE() { return AOE; }

    public int getDuration() { return duration;}

    /*
     * General use type for single hit techniques
     * Check if the technique will lower TP below zero, if not then do
     * the technique.
     */

    public double use(Actor attacker, Vector<Actor> opponents, int target) {
        double threatBuilt = 0;
        Actor opponent = opponents.get(target);
        if((attacker.getTechniquePoints() - TPCost) >= 0) {
            Boolean hit = attacker.hitCalculator();
            out.printToConsole(attacker.getName() + " used " + name + "!");
            if (hit) {
                //Attack lands
                opponent.subHealth(this.damage);
                out.printToConsole(attacker.getName() + " dealt " + this.damage + " damage to " + opponent.getName());
                threatBuilt = this.damage;
                if (opponent.getHealth() <= 0) {
                    opponent.cleanDeath();
                }
            } else {
                //Attack missed
                out.printToConsole(attacker.getName() + " missed their attack!");
            }
            attacker.subTechniquePoints(TPCost);
        } else {
            out.printToConsole("Not enough TP!");
            return -1;
        }
        return threatBuilt;
    }

    public Boolean isStrongAgainst(Vector<Actor> opponents, int target) {
        Vector<Element> strengths = opponents.get(target).getStatus().getStrengths();
        for(int i = 0; i < strengths.size(); i++) {
            if(element.equals(strengths.get(i))) {
                return true;
            }
        }
        return false;
    }
}
