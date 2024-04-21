package lsg.consumables.drinks;

public class Whisky extends Drink {

    public Whisky() {
        super("12 years old Oban", 150);
    }
    @Override
    public String toString() {
        return "12 years old Oban [" + getCapacity() + " stamina point(s)]";   
    }
}
