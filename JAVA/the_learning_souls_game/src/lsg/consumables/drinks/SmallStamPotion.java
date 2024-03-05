package lsg.consumables.drinks;

public class SmallStamPotion extends Drink {
    
    public SmallStamPotion(){
    super("Small Stamina Potion", 20);
    }

    @Override
    public String toString() {
        return "Small Stamina Potion [" + getCapacity() + " stamina point(s)]";
    }
}
