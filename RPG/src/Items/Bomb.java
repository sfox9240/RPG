package Items;
import Character.Character;
/**
 * Created by piano_000 on 5/23/2015.
 */
public class Bomb extends Item {

    protected int damage;

    public Bomb() {
        name = "Bomb";
        description = "A small throwable bomb.";
        damage = 25;
        intent = Intent.HARM;
    }

    @Override
    public void use(Character caster, Character target) {
        Boolean hit = caster.hitCalculator();
        if (hit) {
            //Attack lands
            System.out.println(caster.getName() + " used " + name + "!");
            target.setHealth(target.getHealth() - damage);
            System.out.println(caster.getName() + " dealt " + damage + " damage to " + target.getName());
            if (target.getHealth() <= 0) {
                System.out.println(target.getName() + " has been felled!");
            }
        } else {
            //Attack missed
            System.out.println(caster.getName() + " missed their attack!");
        }
        caster.getItems().remove(this);
    }
}
