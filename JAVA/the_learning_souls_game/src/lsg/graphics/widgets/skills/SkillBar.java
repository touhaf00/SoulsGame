package lsg.graphics.widgets.skills;

import java.util.LinkedHashMap;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class SkillBar extends HBox {
private static LinkedHashMap<KeyCode, String> DEFAULT_BINDING = new LinkedHashMap<>(); 
static{
        DEFAULT_BINDING.put(KeyCode.DIGIT1, "&");
        DEFAULT_BINDING.put(KeyCode.DIGIT2, "é");
        DEFAULT_BINDING.put(KeyCode.DIGIT3, "\"");
        DEFAULT_BINDING.put(KeyCode.DIGIT4, "'");
        DEFAULT_BINDING.put(KeyCode.DIGIT5, "(");
    }

    private SkillTrigger[] triggers ;  
    private ConsumableTrigger consumableTrigger = new ConsumableTrigger(KeyCode.C, "c" , null, null);

    public ConsumableTrigger getConsumableTrigger() {
        return consumableTrigger;
    }

    public SkillBar() {
        this.setSpacing(10);
        this.setPrefHeight(110);
        init();
        setAlignment(Pos.CENTER);
    }

    private void init() {
        triggers = new SkillTrigger[DEFAULT_BINDING.size()];
        int i = 0;
        for(KeyCode keyCode : DEFAULT_BINDING.keySet()) {
            triggers[i] = new SkillTrigger(keyCode, DEFAULT_BINDING.get(keyCode), null, null);
            this.getChildren().add(triggers[i]);
            i++;
        }

        Rectangle spacer = new Rectangle(0, 0, 100, 50);
        spacer.setOpacity(0);
        this.getChildren().add(spacer);

        this.getChildren().add(consumableTrigger);
    }
    
    public SkillTrigger getTrigger(int slot){
        return triggers[slot];
    }
    public void process(KeyCode code){
        if(disabledProperty().get()) return;

        if (code == consumableTrigger.getKeyCode())
        {
            consumableTrigger.trigger();
            return;
        }

        for(SkillTrigger trigger : triggers) {
            if(trigger.getKeyCode() == code) {

                trigger.trigger();
                break;
            }
        }
    }
}
    
