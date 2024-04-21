package lsg.helper;

import java.util.Random;

/**
 * Classe représentant un dé. 
 * Génère des nombres aléatoires compris entre 0 et le nombre de faces -1. 
 * 
 * @author Greg
 *
 */
public class Dice {
	
	private int faces ; // nombre de valeurs possibles
	
	private Random random ;
	
	
	/**
	 * @param faces : le nombre de faces du dé
	 */
	public Dice(int faces) {
		this.faces = faces ;
		random = new Random() ;
	}
	
	/**
	 * @return un nombre aléatoire entre 1 et le nombre de faces du dé
	 */
	public int roll(){
		return random.nextInt(faces) ;
	}

	/**
	 * Test de la classe Dice
	 * 
	 * @param args : non utilisés
	 */
	
}
