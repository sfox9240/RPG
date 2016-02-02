package Test.Skills;

import Actor.Actor;
import Actor.Hero;
import FightClasses.Knight;
import FightClasses.Wizard;
import Game.Illness;
import Items.Sword;
import Skills.DoubleStrike;
import Skills.PoisonCloud;
import org.junit.Assert;
import org.junit.Test;

import java.util.Vector;

/**
 * Created by piano_000 on 2/2/2016.
 */
public class PoisonCloudTest {

    @Test
    public void TestInit(){
        PoisonCloud pc = new PoisonCloud();
        Assert.assertNotNull(pc);
    }

    @Test
    public void TestUse() {
        Hero Link = new Hero("Link", new Knight());
        Hero Zelda = new Hero("Zelda", new Wizard());
        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Link.setWeapon(woodenSword);
        Zelda.addSkill(new PoisonCloud());

        Vector<Actor> party = new Vector<Actor>();
        party.add(Link);
        party.add(Zelda);

        Assert.assertEquals(Illness.NONE, Link.getStatus().getIllness());
        double threatcount = Zelda.getSkills().get(1).use(Zelda, party, 0);
        Assert.assertNotNull(threatcount);
        Assert.assertEquals(0, threatcount, 0);
        Assert.assertEquals(Illness.POISON, Link.getStatus().getIllness());
        Assert.assertEquals(new PoisonCloud().getDuration(), Link.getStatus().getIllnessDuration(), 0);
    }

    @Test
    public void TestUseFailure() {
        Hero Link = new Hero("Link", new Knight());
        Hero Zelda = new Hero("Zelda", new Wizard());
        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Link.setWeapon(woodenSword);
        Zelda.addSkill(new PoisonCloud());

        Vector<Actor> party = new Vector<Actor>();
        party.add(Link);
        party.add(Zelda);

        Assert.assertEquals(Illness.NONE, Link.getStatus().getIllness());
        Zelda.setTechniquePoints(0);
        double returnError = Zelda.getSkills().get(1).use(Zelda, party, 0);
        Assert.assertNotNull(returnError);
        Assert.assertEquals(-1, returnError, 0);

    }
}
