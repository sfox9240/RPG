package Items;
import Actor.*;

public abstract class Item {

	protected String name;
	protected String description;
	protected Intent intent;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public Intent getIntent() { return intent;}

	public abstract void use(Actor caster, Actor target);
}
