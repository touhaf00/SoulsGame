package lsg.armor;

public class ArmorItem {
	
	private String name ;
	
	private float armorValue ;
	
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
	
}
