package lsg.exceptions;
import lsg.consumables.Consumable;

public class ConsumeEmptyException extends ConsumeException {
    public ConsumeEmptyException(Consumable consumable) {
        super(consumable.getName() + " is empty!", consumable);
}
}
