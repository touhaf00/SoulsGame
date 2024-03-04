package lsg.buffs.rings;

import lsg.weapons.Sword;

public class RingOfSwords extends Ring{
	
	public RingOfSwords() {
		super("Ring of Swords", 10) ;
	}
	
	@Override
	public float computeBuffValue() {
		if (hero != null && (hero.getWeapon() instanceof Sword) )  return power ;
		else return 0f ;
		
	}
	

}
