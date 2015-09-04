package Test.Skills;
import Actor.Hero;
import FightClasses.Knight;
import FightClasses.Wizard;
import Items.Sword;
import Skills.DoubleStrike;
import org.junit.*;
/**
 * Created by piano_000 on 9/4/2015.
 */
public class DoubleStrikeTest {

    @Test
    public void TestInit(){
        DoubleStrike ds = new DoubleStrike();
        Assert.assertNotNull(ds);
    }

    @Test
    public void TestUse() {
        Hero Link = new Hero("Link", new Knight());
        Hero Zelda = new Hero("Zelda", new Wizard());
        Sword woodenSword = new Sword("Wooden Sword", "Just a sword made of wood", false);
        Link.setWeapon(woodenSword);

        double threatcount = Link.getSkills().get(0).use(Link, Zelda);
        System.out.println(threatcount);
        //Assert.assertNotNull(threatcount);
    }
}
