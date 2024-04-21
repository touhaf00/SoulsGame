package lsg.bags;

import java.util.HashSet;

import lsg.exceptions.BagFullException;


public class Bag {
    private int capacity;
    private int weight;
    private HashSet<Collectible> items;
    public static final String BULLET_POINT = "\u2219";
    public Bag(int capacity) {
        this.capacity = capacity;
        this.weight = 0;
        this.items = new HashSet<>();
    }
    public int getCapacity() {
        return capacity;
    }
    public int getWeight() {
        return weight;
    }
    public void push(Collectible item) throws BagFullException{
        if (weight + item.getWeight() > capacity) {
        throw new BagFullException(this);
        }
        if (weight + item.getWeight() <= capacity) {
            items.add(item);
            weight += item.getWeight();
        }
    }
    public	Collectible	pop(Collectible	item){
        if (items.contains(item)) {
            items.remove(item);
            weight -= item.getWeight();
            return item;
        } else {
            return null;
        }
    }
    public boolean contains(Collectible	item){
        return items.contains(item);
    }
    public Collectible[] getItems() {
        return items.toArray(new Collectible[0]);
    }
    @Override
    public String toString(){
        String bagS = (getClass().getSimpleName() + " [ "  + items.size() + " items | " + weight + "/" + capacity + " kg ]:\n");
        if(!items.isEmpty()){
           for (Collectible item : items){
            bagS += (Bag.BULLET_POINT + item.toString() + " [" + item.getWeight() +" kg]\n" );
           }
        }
        else{
            bagS += (Bag.BULLET_POINT + "empty \n");
        }
        return bagS;
    }
    public static void transfer(Bag from, Bag into) {
        if (from == null || into == null) {
            return;
        }
        Collectible[] itemsToTransfer = from.getItems();
        for (Collectible item : itemsToTransfer) {
            int initialWeightInto = into.getWeight();
            try {
                into.push(item);
                int finalWeightInto = into.getWeight();
                if (finalWeightInto > initialWeightInto) {
                    from.pop(item);
                }
            } catch (BagFullException e) {
            }
        }
    }
    
}
