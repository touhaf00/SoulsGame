package lsg;

import java.util.Scanner;
import lsg.armor.DragonSlayerLeggings;
import lsg.buffs.rings.DragonSlayerRing;
import lsg.buffs.rings.RingOfDeath;
import lsg.buffs.talismans.MoonStone;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Lycanthrope;
import lsg.characters.Monster;
import lsg.consumables.Consumable;
import lsg.consumables.MenuBestOfV4;
import lsg.consumables.repair.RepairKit;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;

public class LearningSoulsGame {
	
	Scanner scanner = new Scanner(System.in);
	
	Hero hero;
	Monster monster;
	
	public LearningSoulsGame() {
		play();
	}
	
	private void play() {
		hero = new Hero();
		hero.setWeapon(new Sword());
		hero.setArmorItem(new DragonSlayerLeggings(), 1);
		hero.setRing(new RingOfDeath(), 1);
		hero.setRing(new DragonSlayerRing(), 2);
		
		monster = new Lycanthrope();
		monster.setTalisman(new MoonStone());
		
		fight1v1();
	}
	
	private void fight1v1() {
		refresh();
		
		Character aggressor = hero;
		Character target = monster;
		String action = null; // TODO will be used in another version
		int attack, hit;
		Character tmp;
		
		while (hero.isAlive() && monster.isAlive()) {
			System.out.println();
			System.out.println("Hit enter key for next move > ");
			action = scanner.nextLine();
			
			attack = aggressor.attack();
			
			hit = target.getHitWith(attack);
			System.out.printf("%s attacks %s with %s (ATTACK:%d | DMG : %d)", 
			                  aggressor.getName(), target.getName(), aggressor.getWeapon().getName(), attack, hit);
			
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
	
	private void refresh() {
		hero.printStats();
		monster.printStats();
	}
	
	public void createExhaustedHero() {
		hero = new Hero();
		hero.getHitWith(99);
		Weapon grosseArme = new Weapon("Grosse Arme", 100, 100, hero.getStamina(), 1);
		hero.setWeapon(grosseArme);
		hero.attack();
		System.out.println("Create exhausted hero : \n" + hero + "\n");
		
	}
    public void aTable() {
		// Instantiate MenuBestOfV4
		MenuBestOfV4 menuBestOfV4 = new MenuBestOfV4();
		// Loop through each consumable in the menu and have the hero consume it
		for (Consumable consumable : menuBestOfV4) {
			if (consumable instanceof RepairKit) {
				RepairKit repairKit = (RepairKit) consumable;
				System.out.println(hero.getName() + " repairs " + hero.getWeapon().toString() +
				" with " + consumable.getName() + " [" + repairKit.getCapacity() + " durability point(s)]");
				System.out.println("Apres utilisation : " + consumable.toString())  ;
			}  
			else {
				hero.use(consumable);
			    hero.printStats();
			System.out.println("Apres utilisation : " + consumable.toString()+ "\n")  ;
			
		}
	}
	System.out.println(hero.getWeapon().toString());
}
	public static void main(String[] args) {
		LearningSoulsGame game = new LearningSoulsGame();
        game.createExhaustedHero();
        game.aTable();

	}
}
