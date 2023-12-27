package lsg.consumables.repair;
import lsg.consumables.Consumable;

public class RepairKit extends Consumable {

    public RepairKit() {
        super("Repair kit", 10,"durability");
    }
   @Override
    public int use() {
        if (getCapacity() > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}

