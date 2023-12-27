package lsg.consumables.food;

public class Americain extends Food {
    public Americain() {
        super("Friterie 2000's Best of the Best", 3000);
    }
    @Override
    public String toString() {
        return "Friterie 2000's Best of the Best [" + getCapacity() + " life point(s)]";
    }
}
