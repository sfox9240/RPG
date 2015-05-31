package Items;
import Character.Character;

/**
 * Created by piano_000 on 5/29/2015.
 */
public class MagicPotion extends Item {

    protected int restore;

    public MagicPotion() {
        name = "Green Herb";
        description = "A natural herb that heals 20 HP";
        restore = 25;
        this.intent = Intent.RESTORE;
    }

    @Override
    public void use(Character caster, Character target) {
        if(target.getHealth() > 0) {
            target.setTechniquePoints(target.getTechniquePoints() + restore);
            System.out.println(target.getName() + " restored " + restore + " TP");
            caster.getItems().remove(this);
        }
    }
}
