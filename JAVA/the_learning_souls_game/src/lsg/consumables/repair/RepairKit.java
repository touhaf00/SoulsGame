package lsg.consumables.repair;
import lsg.consumables.Consumable;

public class RepairKit extends Consumable {

    public RepairKit() {
        super("Repair kit", 10,"durability");
    }
   @Override
    public int use() {
        if (this.getCapacity() == 0) return 0;
        this.setCapacity(this.getCapacity() - 1);
        return 1;
    }
}


