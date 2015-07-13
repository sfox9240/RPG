package Items;
import Augments.Augment;
import Actor.*;

public abstract class Weapon extends Item {

	protected Boolean twoHanded;
	protected double damage;
	protected Augment augments;
	
	public Boolean getTwoHanded(){
		return twoHanded;
	}
	
	public double getDamage(){
		return damage + augments.getModifier();
	}
	
	public String getAugmentName() {
		return augments.getName();
	}
	
	public double getAugmentDmg() {
		return augments.getModifier();
	}
	
	public void setAugment(Augment a) {
		augments = a;
	}
 	
	public void getDetails() {
		System.out.println(name + " is " + description + ". It does " + damage + " damage.");
	}

	public void use(Actor caster, Actor target) {
		System.out.println("Cannot use " + name + ".");
	}
}
