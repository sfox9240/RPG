package Actor;

import FightClasses.BattleClass;
import Items.Weapon;
import Skills.*;

/**
 * Created by piano_000 on 1/30/2016.
 * This is just an actor for testing who has access to all skills and items
 */
public class AllPlayer extends Actor {

    public AllPlayer(String name) {
        this.name = name;
        weapon = null;
        health = 1000;
        maxHealth = 1000;
        techniquePoints = 1000;
        maxTechniquePoints = 1000;
        skills.add(new DoubleStrike());
        skills.add(new Fireball());
        skills.add(new FireBreath());
        skills.add(new TripleStrike());
        skills.add(new WeakHealAll());
        skills.add(new PoisonCloud());
    }

    public int getDamageMod() {
        return 5;
    }

    public void setWeapon(Weapon w) {
        this.weapon = w;
    }

    public double getCombatDmg() {
        return getDamageMod() + weapon.getDamage();
    }
}
