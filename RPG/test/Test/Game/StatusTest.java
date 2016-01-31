package Test.Game;

import Actor.Actor;
import Actor.Hero;
import FightClasses.BanditKing;
import FightClasses.Knight;
import FightClasses.Wizard;
import Game.State;
import Game.Status;
import Items.Bow;
import Items.Sword;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

/**
 * Created by piano_000 on 1/26/2016.
 */
public class StatusTest {

    Hero Link = null;
    Hero Zelda = null;
    Hero Ganon = null;
    Vector<Actor> party = null;

    @Before
    public void setup() {
        Link = new Hero("Link", new Knight());
        Zelda = new Hero("Zelda", new Wizard());
        Ganon = new Hero("Ganon", new BanditKing());

        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Bow woodenBow = new Bow("Wooden Bow", "A nice wooden bow", true);

        Link.setWeapon(woodenSword);
        Zelda.setWeapon(woodenBow);
        Ganon.setWeapon(woodenSword);

        party = new Vector<>();
        party.add(Link);
        party.add(Zelda);
        party.add(Ganon);
    }

    @After
    public void teardown() {
        party.removeAllElements();
    }

    @Test
    public void TestSetState() {
        Assert.assertEquals(State.NORMAL, party.get(0).getStatus().getState());
        party.get(0).getStatus().setState(State.FROZEN, 3);
        Assert.assertEquals(State.FROZEN, party.get(0).getStatus().getState());
    }

    @Test
    public void TestElapseState() {
        Assert.assertEquals(State.NORMAL, party.get(0).getStatus().getState());
        party.get(0).getStatus().setState(State.FROZEN, 3);
        Assert.assertEquals(State.FROZEN, party.get(0).getStatus().getState());
        Assert.assertEquals(3, party.get(0).getStatus().getStateDuration());
        party.get(0).getStatus().elapseStete(1);
        Assert.assertEquals(2, party.get(0).getStatus().getStateDuration());
    }

}
