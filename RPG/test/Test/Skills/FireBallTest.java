package Test.Skills;

import Actor.Actor;
import Actor.Hero;
import Actor.Dragon;
import FightClasses.Knight;
import FightClasses.Wizard;
import Game.Element;
import Items.Sword;
import Skills.FireBreath;
import Skills.FireBall;
import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

/**
 * Created by piano_000 on 2/2/2016.
 */
public class FireBallTest {

    @Test
    public void TestInit(){
        FireBall fb = new FireBall();
        Assert.assertNotNull(fb);
    }

    @Test
    public void TestUse() {
        Hero Link = new Hero("Link", new Knight());
        Hero Zelda = new Hero("Zelda", new Wizard());
        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Link.setWeapon(woodenSword);

        Vector<Actor> party = new Vector<Actor>();
        party.add(Link);
        party.add(Zelda);

        Dragon dragon = new Dragon();
        dragon.getStatus().addStrength(Element.FIRE);
        dragon.getStatus().addWeakness(Element.ICE);
        Vector<Actor> enemy = new Vector<Actor>();
        enemy.add(dragon);

        double threatcount = Zelda.getSkills().get(0).use(Zelda, enemy, 0);
        Assert.assertNotNull(threatcount);
    }

    @Test
    public void TestUseFailure() {
        Hero Link = new Hero("Link", new Knight());
        Hero Zelda = new Hero("Zelda", new Wizard());
        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Link.setWeapon(woodenSword);

        Vector<Actor> party = new Vector<Actor>();
        party.add(Link);
        party.add(Zelda);

        Dragon dragon = new Dragon();
        Vector<Actor> enemy = new Vector<Actor>();
        enemy.add(dragon);
        Zelda.setTechniquePoints(0);
        double returnError = Zelda.getSkills().get(0).use(Zelda, enemy, 0);
        Assert.assertEquals(-1, returnError, 0);
    }

    @Test
    public void TestStrongAgainst() {
        Hero Link = new Hero("Link", new Knight());
        Hero Zelda = new Hero("Zelda", new Wizard());
        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Link.setWeapon(woodenSword);

        Vector<Actor> party = new Vector<Actor>();
        party.add(Link);
        party.add(Zelda);

        Dragon dragon = new Dragon();
        dragon.getStatus().addStrength(Element.FIRE);
        dragon.getStatus().addWeakness(Element.ICE);
        Vector<Actor> enemy = new Vector<Actor>();
        enemy.add(dragon);

        Boolean strong = Zelda.getSkills().get(0).isStrongAgainst(enemy, 0);
        Assert.assertEquals(true, strong);

    }

}
