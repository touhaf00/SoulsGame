package lsg.armor;

import lsg.bags.Collectible;

public class ArmorItem implements Collectible {
	
	private String name ;
	
	private float armorValue ;
	private static final int WEIGHT = 4;

	
	public ArmorItem(String name, float armorValue) {
		this.name = name ;
		this.armorValue = armorValue ;
	}
	
	public String getName() {
		return name;
	}
	
	public float getArmorValue() {
		return armorValue;
	}

	@Override
	public String toString() {
		return getName() + "(" + getArmorValue() + ")" ;
	}

	@Override
	public int getWeight() {
		return WEIGHT;
	}
	
}
