package lsg.consumables.food;

public class Hamburger extends Food{

    public Hamburger() {
        super("Uncle Greg's spicy Maroilles burger", 40);
    }
    @Override
    public String toString() {
        return "Uncle Greg's spicy Maroilles burger [" + getCapacity() + " life point(s)]";
    }
}
