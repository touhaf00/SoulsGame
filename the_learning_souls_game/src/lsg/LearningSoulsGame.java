package lsg;

import java.util.Scanner;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Monster;
import lsg.consumables.food.Hamburger;
import lsg.exceptions.ConsumeEmptyException;
import lsg.exceptions.ConsumeNullException;
import lsg.exceptions.ConsumeRepairNullWeaponException;
import lsg.exceptions.StaminaEmptyException;
import lsg.exceptions.WeaponBrokenException;
import lsg.exceptions.WeaponNullException;
import lsg.weapons.Claw;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;

public class LearningSoulsGame {
	
	Scanner scanner = new Scanner(System.in);
	
	Hero hero;
	Monster monster;
	public static final String BULLET_POINT = "\u2219";
	
	public LearningSoulsGame() {
		title();
		init();
	}
	private void init(){

        hero = new Hero();
        Hamburger h = new Hamburger();
        hero.setConsumable(h);
        hero.setWeapon(new Sword());
        monster = new Monster();
        monster.setWeapon(new Claw());
        

    }
	
	
	private void fight1v1()  {
		refresh();
		
		Character aggressor = hero;
		Character target = monster;
		int action = 0; 
		int attack = 0, hit;
		Character tmp;
		
		while (hero.isAlive() && monster.isAlive()) {
			System.out.println();
			if(aggressor == hero){
				System.out.println("Hero's action for next move : (1) attack | (2) consume > ");
				action=scanner.nextInt();
				if (action == 1 || action == 2) {
					if (action == 1) {
						try {
							attack = aggressor.attack();
						} catch (WeaponNullException e) {
							System.out.println("ACTION HAS NO EFFECT : " + e.getMessage());
							attack = 0; // Continuez avec une valeur d'attaque de 0
						} catch (WeaponBrokenException f) {
							System.out.println("WARNING : " + f.getMessage());
							attack= 0;
						}catch (StaminaEmptyException g){
							System.out.println("ACTION HAS NO EFFECT : " + g.getMessage());
							attack = 0;
						}
						hit = target.getHitWith(attack);
						String weaponName = (aggressor.getWeapon() != null) ? aggressor.getWeapon().getName() : "null";
						System.out.printf("%s attacks %s with %s (ATTACK:%d | DMG : %d)", 
										aggressor.getName(), target.getName(), weaponName, attack, hit);
					} else {
						try {
							aggressor.consume();
						} catch (ConsumeNullException h) {
							System.out.println("IMPOSSIBLE ACTION : " + h.getMessage());
						} catch (ConsumeEmptyException j){
							System.out.println("ACTION HAS NO EFFECT : " + j.getMessage());
						} catch(ConsumeRepairNullWeaponException k) {
							System.out.println("IMPOSSIBLE ACTION : " + k.getMessage() );
						}
					}
				}
			}
			else{
				try {
					attack = aggressor.attack();
				} catch (WeaponNullException e) {
					System.out.println("ACTION HAS NO EFFECT : " + e.getMessage());
					attack = 0; // Continuez avec une valeur d'attaque de 0
				}catch (WeaponBrokenException f) {
					System.out.println("WARNING : " + f.getMessage());
					attack= 0;
				}catch (StaminaEmptyException g){
					System.out.println("ACTION HAS NO EFFECT : " + g.getMessage());
					attack = 0;
				}
				hit = target.getHitWith(attack);
				String weaponName = (aggressor.getWeapon() != null) ? aggressor.getWeapon().getName() : "null";
				System.out.printf("%s attacks %s with %s (ATTACK:%d | DMG : %d)",
								  aggressor.getName(), target.getName(), weaponName, attack, hit);
			}
			System.out.println();
			refresh();
			
			tmp = aggressor;
			aggressor = target;
			target = tmp;
		}
		
		Character winner = (hero.isAlive()) ? hero : monster;
		System.out.println();
		System.out.println("--- " + winner.getName() + " WINS !!! ---");
	}
	
	
    
	
	private void refresh(){

        hero.printStats();
        System.out.println(hero.armorToString());
        hero.printRings();
        hero.printConsumable();
        System.out.println("WEAPON : "+ hero.getWeapon());
        hero.printBag();
        monster.printStats();
        System.out.println("\n");

    }
	
	private void createExhaustedHero(){

        hero = new Hero();
        hero.getHitWith(99);
        Weapon w = new Weapon("Grosse Arme", 0, 0, 1000, 100);
        hero.setWeapon(w);
        try {
            hero.attack();
        }catch(WeaponNullException e){
        
         e.printStackTrace();
            System.out.println(e.getMessage());

        }catch (WeaponBrokenException f) {
			System.out.println(f.getMessage());
		} catch (StaminaEmptyException g){
			System.out.println("ACTION HAS NO EFFECT : " + g.getMessage());
		}
        hero.printStats();
	}
    

	public void title() {
		String title = "THE LEARNING SOULS GAME";
		String hash = new String(new char[28]).replace("\0", "#");
		System.out.println("#" + hash);
		System.out.println("#  " + title + "  #");
		System.out.println("#" + hash);
	}
	
// public void testExceptions(){
// 	hero.setBag(null);
// 	this.fight1v1();
// }
// 	public static void main(String[] args) {
// 		LearningSoulsGame game = new LearningSoulsGame();
//         game.init();
// 		game.testExceptions();
// 		game.createExhaustedHero();
// 	}
}

