package lsg.characters;

import java.util.Locale;

import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
import lsg.helper.Dice;
import lsg.weapons.Weapon;

public abstract class Character {

	private static final String LIFE_STAT_STRING = "life";
	private static final String STAM_STAT_STRING = "stamina";
	private static final String PROTECTION_STAT_STRING = "protection";
    private static final String BUFF_STAT_STRING = "buff";
	private static String MSG_ALIVE = "(ALIVE)" ;
	private static String MSG_DEAD = "(DEAD)" ;
	
	private String name ; // Nom du personnage
	
	private int maxLife, life ; 		// Nombre de points de vie restants
	private int maxStamina, stamina ;	// Nombre de points d'action restants
	
	private Weapon weapon ;
	
	private Dice dice101 = new Dice(101) ;
	
	public Character(String name) {
		this.name = name ;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxLife() {
		return maxLife;
	}
	
	protected void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
	
	public int getLife() {
		return life;
	}
	
	protected void setLife(int life) {
		this.life = life;
	}
	
	public int getMaxStamina() {
		return maxStamina;
	}
	
	protected void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	protected void setStamina(int stamina) {
		this.stamina = stamina;
	}
	
	public boolean isAlive(){
		return life > 0 ;
	}
	
	public void printStats(){
		System.out.println(this);
	}
	
	@Override
	public String toString() {
		
		String classe = getClass().getSimpleName() ;
		String life = String.format("%5d", getLife()) ; 
		String stam = String.format("%5d", getStamina()) ; 
		String protection = String.format(Locale.US, "%6.2f", computeProtection()) ;
		String buff = String.format(Locale.US, "%6.2f", computeBuff()) ;
		
		String msg = String.format("%-20s %-20s %s:%-10s %s:%-10s %s:%-10s %s:%-10s", "[ " + classe + " ]", getName(), Character.LIFE_STAT_STRING,life,Character.STAM_STAT_STRING, stam, Character.PROTECTION_STAT_STRING, protection, Character.BUFF_STAT_STRING, buff) ;
		
		String status ;
		if(isAlive()){
			status = MSG_ALIVE ;
		}else{
			status = MSG_DEAD ;
		}
		
		return msg + status ;
	}
	
	public int attack(){
		return attackWith(this.getWeapon()) ;
	}
	
	/**
	 * Calcule une attaque en fonction d'une arme.
	 * Le calcul dépend des statistiques de l'arme, de la stamina (restante) du personnage et des buffs eventuels
	 * 
	 * @param weapon : l'arme utilisée.
	 * @return la valeur de l'attaque eventuellement buffée ; 0 si l'arme est cassée.
	 */
	protected int attackWith(Weapon weapon){
		int min = weapon.getMinDamage() ;
		int max = weapon.getMaxDamage() ;
		int cost = weapon.getStamCost() ;
		
		int attack = 0 ;
		
		if(!weapon.isBroken()){
			attack = min + Math.round((max-min) * dice101.roll() / 100.f) ;
			int stam = getStamina() ;
			if(cost <= stam){ // il y a assez de stam pour lancer l'attaque
				setStamina(getStamina()-cost);
			}else{
				attack = Math.round(attack * ((float)stam / cost)) ;
				setStamina(0);
			}	
			weapon.use();
		}
		
		return attack + Math.round(attack*computeBuff()/100);
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	/**
	 * @return le nombre de points de buff du personnage
	 */
	protected abstract float computeBuff() ; 
	
	/**
	 * @return le nombre de points de protection du personnage
	 */
	protected abstract float computeProtection() ;
	
	/**
	 * Calcule le nombre de PV retires en tenant compte de la protection
	 * @param value : le montant des degats reçus
	 * @return le nombre de PV effectivement retires (value reduite par la protecion si assez de vie ; le reste de la vie sinon)
	 */
	public int getHitWith(int value){
		
		int life = getLife() ;
		int dmg ;
		float protection = computeProtection() ;
		if(protection > 100) protection = 100 ; // si la protection depasse 100, elle absorbera 100% de l'attaque
		value = Math.round(value - (value * protection / 100)) ;
		dmg = (life > value) ? value : life ; 
		setLife(life-dmg);
		return dmg ;
				
	}
	private void drink(Drink drink){
		System.out.println(getName() + " drinks " + drink.toString());
		int staminaToAdd = drink.use();
        int currentStamina = getStamina();
        int maxStamina = getMaxStamina();
        if (currentStamina + staminaToAdd <= maxStamina) {
            setStamina(currentStamina + staminaToAdd);
        } else {
            setStamina(maxStamina);
        }
		
    }
	private void eat(Food food){
		System.out.println(getName() + " eats " + food.toString());
		int lifeToAdd = food.use();
        int currentLife = getLife();
        int maxLife = getMaxLife();
        if (currentLife + lifeToAdd <= maxLife) {
            setLife(currentLife + lifeToAdd);
        } else {
            setLife(maxLife);
        }
	}
	public void	 use(Consumable	 consumable){
		if (consumable instanceof Drink) {
            drink((Drink) consumable);
        } else if (consumable instanceof Food) {
            eat((Food) consumable);
		}
	    if (consumable instanceof RepairKit) {
				repairWeaponWith((RepairKit) consumable);
		} 
			
	}

    private void repairWeaponWith(RepairKit kit) {
		Weapon weapon = getWeapon();
		if (weapon != null && !weapon.isBroken()) {
			int initialDurability = weapon.getDurability();
			int repairPoints = kit.use();
			weapon.repairWith(kit);
			repairPoints = weapon.getDurability() - initialDurability;
			System.out.println(getName() + " repairs " + weapon.getName() + " " + weapon + " with " + kit.getName() +
					" [" + repairPoints + " durability point(s)]");
		}
	}
}