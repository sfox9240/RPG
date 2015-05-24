package Items;
import Character.Character;
/**
 * Created by piano_000 on 5/22/2015.
 */
public class GreenHerb extends Item {

    protected int heal;

    public GreenHerb() {
        name = "Green Herb";
        description = "A natural herb that heals 20 HP";
        heal = 20;
        this.intent = Intent.HEAL;
    }

    public int getHeal() {
        return heal;
    }

    /*
     Only works on non-dead party members
     */
    @Override
    public void use(Character caster, Character target) {
        if(target.getHealth() > 0) {
            target.setHealth(target.getHealth() + heal);
            System.out.println(target.getName() + " was healed for " + heal + " HP");
            caster.getItems().remove(this);
        }
    }
}
