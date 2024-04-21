package lsg.consumables;

import javafx.beans.property.SimpleBooleanProperty;
import lsg.bags.Collectible;
import lsg.exceptions.ConsumeEmptyException;

public class Consumable implements Collectible {
    private String name;
    private int capacity;
    private String stat;
    private SimpleBooleanProperty isEmpty;


    private static final int WEIGHT= 1;
    public Consumable (String name,int capacity,String stat){
        this.name=name;
        this.capacity=capacity;
        this.stat=stat;
        this.isEmpty = new SimpleBooleanProperty(capacity==0);
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
        isEmpty.set(capacity==0);
    }
    public int use() throws ConsumeEmptyException{
        int capacity = getCapacity();
        if (capacity == 0) { throw new ConsumeEmptyException(this); }
        setCapacity(0);
        return capacity;
    }
    @Override
    public String toString() {
        return name + " [" + capacity + " " + stat + " point(s)]";
    }
    @Override
    public int getWeight() {
        return WEIGHT;
        
    }
    public SimpleBooleanProperty isEmpty() {
        return isEmpty;
    }
}



