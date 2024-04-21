package lsg.exceptions;
import lsg.consumables.Consumable;

public class ConsumeException extends Exception {
   
    private Consumable consumable;
    private String message;

    public ConsumeException(String message, Consumable consumable){
        this.message = message;
        this.consumable = consumable;
    }

    public Consumable getConsumable(){

        return this.consumable;

    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
