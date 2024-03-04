package lsg.armor;


public class DragonSlayerLeggings extends ArmorItem  {

	private static String ARMOR_NAME = "Dragon Slayer Leggings" ;
	private static float ARMOR_VALUE = 10.2f ; 
	private static final int WEIGHT = 3;
	
	public DragonSlayerLeggings() {
		super(ARMOR_NAME, ARMOR_VALUE) ;
	}
	@Override
	public int getWeight() {
		return WEIGHT;
	}
}
