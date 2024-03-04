package lsg.characters;

import java.util.Locale;

import javafx.beans.property.SimpleDoubleProperty;
import lsg.bags.Bag;
import lsg.bags.Collectible;
import lsg.bags.SmallBag;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.helper.Dice;
import lsg.weapons.Weapon;

public abstract class Character {

	private static final String LIFE_STAT_STRING = "life";
	private static final String STAM_STAT_STRING = "stamina";
	private static final String PROTECTION_STAT_STRING = "protection";
    private static final String BUFF_STAT_STRING = "buff";
	private static String MSG_ALIVE = "(ALIVE)" ;
	private static String MSG_DEAD = "(DEAD)" ;
	
	private String name ; 
	
	private int maxLife, life ; 		
	private int maxStamina, stamina ;	
	
	private Weapon weapon ;
	
	private Dice dice101 = new Dice(101) ;
	private Consumable consumable;
	private Bag bag;
	
    private SimpleDoubleProperty lifeRate;
	private SimpleDoubleProperty stamRate;

	
	public Character(String name) {
		this.name = name ;
		bag=new SmallBag();
		lifeRate = new SimpleDoubleProperty();
		stamRate = new SimpleDoubleProperty();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public SimpleDoubleProperty lifeRateProperty(){
		return lifeRate;
	}

	public SimpleDoubleProperty stamRateProperty(){
		return stamRate;
	}

	public Bag getBag(){
		return bag;
	}

	public Consumable getConsumable(){
		return consumable;
	}
	public void setConsumable(Consumable consumable){
		this.consumable= consumable;
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
		lifeRate.set((double)life/maxLife);
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
		stamRate.set((double)stamina/maxStamina);
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
	
	public int attack() throws WeaponNullException, WeaponBrokenException, StaminaEmptyException{
		return attackWith(this.getWeapon()) ;
	}
	
	/**
     * Attaque avec le Weapon donné
     *
     * @param weapon le weapon d'attaque
     * @return la valeur de l'attaque
     * @throws WeaponNullException si l'arme est nulle
	 * @throws WeaponBrokenException 
	 * @throws StaminaEmptyException 
    */
	protected int attackWith(Weapon weapon) throws WeaponNullException, WeaponBrokenException, StaminaEmptyException {
		if (weapon == null) {
			throw new WeaponNullException();
		}
		if (weapon.isBroken()) {
			throw new WeaponBrokenException(weapon);
        }
		if(stamina == 0){
			throw new StaminaEmptyException();
		}
		
		int min = weapon.getMinDamage();
		int max = weapon.getMaxDamage();
		int cost = weapon.getStamCost();
		
		int attack = 0;
		
		if (!weapon.isBroken()) {
			attack = min + Math.round((max - min) * dice101.roll() / 100.f);
			int stam = getStamina();
			if (cost <= stam) { 
				setStamina(getStamina() - cost);
			} else {
				attack = Math.round(attack * ((float) stam / cost));
				setStamina(0);
			}
			weapon.use();
		}
		
		return attack + Math.round(attack * computeBuff() / 100);
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
		if(protection > 100) protection = 100 ;
		value = Math.round(value - (value * protection / 100)) ;
		dmg = (life > value) ? value : life ; 
		setLife(life-dmg);
		return dmg ;
				
	}
	private void drink(Drink drink) throws ConsumeNullException, ConsumeEmptyException{
		if (drink == null) {
			throw new ConsumeNullException(drink);
		}
		if(drink.getCapacity()==0){
			throw new ConsumeEmptyException(drink);
		}
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
	private void eat(Food food) throws ConsumeNullException, ConsumeEmptyException{
		if(food == null){
			throw new ConsumeNullException(food);
		}
		if(food.getCapacity()==0){
			throw new ConsumeEmptyException(food);
		}
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
	public void	 use(Consumable	 consumable) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException{
		if(consumable == null){
			throw new ConsumeNullException(consumable) ;
		}
		if(consumable.getCapacity()==0){
			throw new ConsumeEmptyException(consumable);
		}
		if (consumable instanceof Drink) {
            drink((Drink) consumable);
        } else if (consumable instanceof Food) {
            eat((Food) consumable);
		}
		if (weapon == null) { throw new ConsumeRepairNullWeaponException(consumable); }
            repairWeaponWith(consumable);
			
	}

	private void repairWeaponWith(Consumable consumable) throws ConsumeNullException, ConsumeEmptyException {
		if (weapon == null) { 
			try {
				throw new WeaponNullException();
			} catch (WeaponNullException e) {
				e.printStackTrace();
			} 
		}
		if (consumable == null) { 
			return; 
		}
		if (isAlive()) {
			if (consumable instanceof lsg.consumables.repair.RepairKit) {
				System.out.printf("%s repairs %s with %s%n", name, weapon.toString(), consumable.toString());
				weapon.repairWith((lsg.consumables.repair.RepairKit) consumable);
			} else {
				System.out.println("The consumable is not a repair kit.");
			}
		}
	}
	

	public void consume() throws ConsumeNullException ,ConsumeEmptyException, ConsumeRepairNullWeaponException{
		use(consumable);
	}
    
	public void pickUp(Collectible item) throws NoBagException, BagFullException{
		if (getBag()==null){
			throw new NoBagException();
		}
		if (item != null) {
			System.out.println(getName() + " picks up " + item.toString());
			getBag().push(item);
		}
	}

	public Collectible pullOut(Collectible item) throws NoBagException{
		if (getBag()==null){
			throw new NoBagException();
		}
		if(item !=null && getBag().contains(item)){
			System.out.println(getName() + " pulls out " + item.toString());
			return getBag().pop(item);
		}
		return null;
	}
	public void printBag(){
			Bag bag = getBag();
			if (bag != null) {
				System.out.println(bag.toString());
			} else {
				System.out.println("BAG : null");
			}
		}
		
	public int getBagCapacity() throws NoBagException{
		if (getBag()==null){
			throw new NoBagException();
		}
		return getBag().getCapacity();
	}
	public int getBagWeight() throws NoBagException{
		if (getBag()==null){
			throw new NoBagException();
		}
		return getBag().getWeight();
	}
	public Collectible[] getBagItems() throws NoBagException{
		if (getBag()==null){
			throw new NoBagException();
		}
		return getBag().getItems();
	}
	public Bag setBag(Bag bag){
		if (bag != null && getBag() != null) {
			Bag.transfer(getBag(), bag);
			System.out.println(getName() + " changes " + getBag().getClass().getSimpleName()
							   + " for " + bag.getClass().getSimpleName());
		} else {
			System.out.println(getName() + " changes " + (getBag() != null ? getBag().getClass().getSimpleName() : "null")
							   + " for " + (bag != null ? bag.getClass().getSimpleName() : "null"));
		}
		this.bag = bag;
		return getBag();
	}
	
	public void equip(Weapon weapon) throws NoBagException{
		if (getBag()==null){
			throw new NoBagException();
		}

        if(bag.contains(weapon)){

            this.weapon = weapon;
            this.pullOut(weapon);
            System.out.println(" and equips it !");
        }
    }

    public void equip(Consumable consumable) throws NoBagException{
		if (getBag()==null){
			throw new NoBagException();
		}

        if(bag.contains(consumable)){

            this.consumable = consumable;
            this.pullOut(consumable);
            System.out.println(" and equips it !");
        }
    }
	private Consumable fastUseFirst(Class<? extends Consumable> type) throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException {

        for(Collectible c : bag.getItems()){

            if(type.isInstance(c)){

                this.use((Consumable) c);
                if(((Consumable) c).getCapacity() == 0){

                    pullOut(c);

                }
                return (Consumable)c;
            }
        }
        return null;
    }
	
	public Drink fastDrink() throws ConsumeNullException, ConsumeEmptyException, NoBagException{
        Drink toRet = null;
        System.out.println(getName() + " drinks FAST :");
        try {
            toRet = (Drink) fastUseFirst(Drink.class);

        }
		catch(ConsumeRepairNullWeaponException e){}

        return toRet;
	}
	public Food fastEat() throws ConsumeNullException, ConsumeEmptyException, NoBagException{
        Food toRet = null;

        System.out.println(getName() + " eats FAST :");
        try {
            toRet = (Food) fastUseFirst(Food.class);
        }catch(ConsumeRepairNullWeaponException e){}

        return toRet;
    }

    public RepairKit fastRepair() throws ConsumeNullException, ConsumeEmptyException, ConsumeRepairNullWeaponException, NoBagException{

        System.out.println(getName() + " repairs FAST :");
        return (RepairKit)fastUseFirst(RepairKit.class);

    }
	public void printConsumable() {
		if (consumable != null) {
			System.out.println("CONSUMABLE : " + consumable.toString());
		} else {
			System.out.println("CONSUMABLE : null");
		}
	}
}
