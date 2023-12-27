package lsg.consumables;

public class Consumable {
    private String name;
    private int capacity;
    private String stat;

    public Consumable (String name,int capacity,String stat){
        this.name=name;
        this.capacity=capacity;
        this.stat=stat;
    }
    public String getName(){
    return name;
    }
    public int getCapacity(){
        return capacity;
    }
    public String getStat(){
        return stat;
    }
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
    public int use(){
        int totalCapacity = capacity;
        capacity = 0;
        return totalCapacity;
    }
    @Override
    public String toString() {
        return name + " [" + capacity + " " + stat + " point(s)]";
    }

}



