package lsg.consumables.food;

public class SuperBerry extends Food {
    
    public SuperBerry() {
        super("Super Berry", 1000);
    }
    
    @Override
    public String toString() {
        return "Super Berry [" + getCapacity() + " life point(s)]";
    }
    
}
