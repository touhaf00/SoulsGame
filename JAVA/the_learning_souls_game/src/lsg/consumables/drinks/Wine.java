package lsg.consumables.drinks;

public class Wine extends Drink{

    public Wine() {
        super("Pomerol 2008", 30);
    }
    @Override
    public String toString() {
        return "Pomerol 2008 [" + getCapacity() + " stamina point(s)]";
    }
}
