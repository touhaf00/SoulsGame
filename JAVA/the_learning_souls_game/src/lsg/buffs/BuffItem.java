package lsg.buffs;

import java.util.Locale;

import lsg.bags.Collectible;

public abstract class BuffItem implements Collectible{
	
	private String name ; 
	private static final int WEIGHT= 1;
	public BuffItem(String name) {
		this.name = name ;
	}
	
	public abstract float computeBuffValue() ;
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.US, "[%s, %.2f]", getName(), computeBuffValue()) ;
	}
	@Override
    public int getWeight() {
        return WEIGHT;
    }
}
