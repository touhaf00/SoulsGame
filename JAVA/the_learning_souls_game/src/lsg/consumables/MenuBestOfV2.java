package lsg.consumables;

import java.util.HashSet;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;

public class MenuBestOfV2 {
       private  java.util.HashSet<Consumable> menu;
    public MenuBestOfV2(){
        menu = new HashSet<>();
        menu.add(new Hamburger());
        menu.add(new Wine());
        menu.add(new Americain());
        menu.add(new Coffee());
        menu.add(new Whisky());
            
            };
    
    @Override
    public String toString() {
        String menuB = "MenuBestOfV2 :\n";
        int index = 1;
        for (Consumable consumable : menu) {
            menuB += index++ + " : " + consumable.toString() + "\n";
        }
        return menuB;
    }
}
