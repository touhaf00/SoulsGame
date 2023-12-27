package lsg.consumables;

import java.util.HashSet;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;

public class MenuBestOfV3 extends HashSet<Consumable> {

    public MenuBestOfV3() {
        add(new Hamburger());
        add(new Wine());
        add(new Americain());
        add(new Coffee());
        add(new Whisky());
    }

    @Override
    public String toString() {
        String menuB = "MenuBestOfV3 :\n";
        int index = 1;
        for (Consumable consumable : this) {
            menuB += index++ + " : " + consumable.toString() + "\n";
        }
        return menuB;
    }
    public static void main(String[] args) {
        MenuBestOfV3 bestOfV3 = new MenuBestOfV3();
        System.out.println(bestOfV3.toString());
    }
}