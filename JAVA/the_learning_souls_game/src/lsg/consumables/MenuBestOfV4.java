package lsg.consumables;

import java.util.LinkedHashSet;
import java.util.List;

import lsg.consumables.drinks.Coffee;
import lsg.consumables.drinks.Whisky;
import lsg.consumables.drinks.Wine;
import lsg.consumables.food.Americain;
import lsg.consumables.food.Hamburger;
import lsg.consumables.repair.RepairKit;

public class MenuBestOfV4 extends LinkedHashSet<Consumable> {
    
    public MenuBestOfV4() {
        add(new Hamburger());
        add(new Wine());
        add(new Americain());
        add(new Coffee());
        add(new Whisky());
        add(new RepairKit());
    }
    @Override
    public String toString() {
        String menuB = "MenuBestOfV4 :\n";
        int index = 1;
        for (Consumable consumable : this) {
            menuB += index++ + " : " + consumable.toString() + "\n";
        }
        return menuB;
    }
    public static void main(String[] args) {
        MenuBestOfV4 bestOfV4 = new MenuBestOfV4();
        System.out.println(bestOfV4.toString());
    }
}
