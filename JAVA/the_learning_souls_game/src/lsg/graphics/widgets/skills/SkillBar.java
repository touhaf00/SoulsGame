package lsg.graphics.widgets.skills;

import java.util.LinkedHashMap;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

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
    }
    public SkillTrigger getTrigger(int slot){
        return triggers[slot];
    }
    public void process(KeyCode code){
        if(disabledProperty().get()) return;
        for(SkillTrigger trigger : triggers) {
            if(trigger.getKeyCode() == code) {
                trigger.trigger();
                break;
            }
        }
    }
}
    