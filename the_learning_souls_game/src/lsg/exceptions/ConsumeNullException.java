package lsg.exceptions;
import lsg.consumables.Consumable;

public class ConsumeNullException extends ConsumeException {
    public ConsumeNullException(Consumable consumable){

        super("No consumable has been equiped !", consumable);

    }

}
