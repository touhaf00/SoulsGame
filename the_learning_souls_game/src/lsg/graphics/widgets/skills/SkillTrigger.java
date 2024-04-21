package lsg.graphics.widgets.skills;


import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class SkillTrigger extends AnchorPane {
    private ImageView view;
    private Label text;
    private KeyCode keyCode;
    private SkillAction action; 
    private ColorAdjust desaturate = new ColorAdjust(0, -1, 0.6, 0);

    public KeyCode getKeyCode() {
        return this.keyCode;
    }
    public SkillAction getAction() {
        return this.action;
    }
    public Label getText() {
        return this.text;
    }
    public Image getImage() {
        return this.view.getImage();
    }
    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }
    public void setAction(SkillAction action) {
        this.action = action;
    }
    public void setText(Label text) {
        this.text = text;
    }
    public void setImage(Image image) {
        this.view.setImage(image);
    }

    

    public SkillTrigger(KeyCode keyCode,String text, Image image, SkillAction action) {
        this.keyCode = keyCode;
        this.action = action;
        this.view = new ImageView(image);
        this.text = new Label(text);  

        buildUI();
        addListeners();
    }
    private void buildUI() {
        getStylesheets().add("lsg/graphics/css/SkillTrigger.css");
        getStyleClass().add("skill");
        

        view.setFitWidth(75);
        view.setFitHeight(75);

        getChildren().add(view);
  
        

    }
    public void trigger(){
        
        if (!isDisabled()) {
            animate();
            if(action != null) action.execute();
        }
    }
    private void addListeners() {
        setOnMouseClicked(event -> trigger());
        disabledProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                setEffect(desaturate);
            }else {
                setEffect(null);
            }
        });
    }

    private void animate() {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), this);
        st.setToX(1.3);
        st.setToY(1.3);
        st.setAutoReverse(true);
        st.play();
    }
}
