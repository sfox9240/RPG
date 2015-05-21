package Skills;
import Game.Element;
/**
 * Created by piano_000 on 5/21/2015.
 */
public abstract class Skill {
    protected String name;
    protected String description;
    protected double damage;
    protected Element element;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getDamage() {
        return damage;
    }

    public Element getElement() {
        return element;
    }

    public abstract void action();
}
