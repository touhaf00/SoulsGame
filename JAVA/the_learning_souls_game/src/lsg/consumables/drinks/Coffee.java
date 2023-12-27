package lsg.consumables.drinks;

public class Coffee extends Drink {

    public Coffee() {
        super("Hot Grandmother Coffee", 10);
    }
    @Override
    public String toString() {
        return "Hot Grandmother Coffee [" + getCapacity() + " stamina point(s)]";
    }
}
