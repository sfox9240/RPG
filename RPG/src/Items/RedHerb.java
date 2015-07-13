package Items;
import Actor.*;
/**
 * Created by piano_000 on 5/22/2015.
 */
public class RedHerb extends Item {

    protected int heal;

    public RedHerb() {
        name = "Red Herb";
        description = "A natural herb that heals 20 HP";
        heal = 40;
        this.intent = Intent.HEAL;
    }

    /*
     Only works on non-dead party members
     */
    @Override
    public void use(Actor caster, Actor target) {
        if(target.getHealth() > 0) {
            target.setHealth(target.getHealth() + heal);
            System.out.println(target.getName() + " was healed for " + heal + " HP");
            caster.getItems().remove(this);
        }
    }
}
