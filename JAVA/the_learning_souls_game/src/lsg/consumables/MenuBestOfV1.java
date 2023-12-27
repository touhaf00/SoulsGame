package lsg.consumables;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;

public class MenuBestOfV1 {
    private Consumable[] menu;
    public MenuBestOfV1(){
        menu = new Consumable[]{
                new Hamburger(),
                new Wine(),
                new Americain(),
                new Coffee(),
                new Whisky()
            };
    }
    @Override
    public String toString(){
        String menuB = "MeunBestOfV1 :\n";
        for(int i = 0 ; i< menu.length; i++){
            menuB += (i+1) + " : " + menu[i].toString() + "\n";
        }
        return menuB;
    }
    public static void main(String[] args) {
        MenuBestOfV1 bestOfV1 = new MenuBestOfV1();
        System.out.println(bestOfV1.toString());
    }
}
