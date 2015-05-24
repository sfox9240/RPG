package Items;
import Character.Character;

public abstract class Item {

	protected String name;
	protected String description;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public abstract void use(Character caster, Character target);
}
