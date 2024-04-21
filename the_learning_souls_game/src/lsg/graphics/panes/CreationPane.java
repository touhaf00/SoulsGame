package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.CSSFactory;
import lsg.graphics.widgets.texts.GameLabel;


public class CreationPane extends VBox {

    private TextField nameField ;
    private final GameLabel playerLabel;

    public TextField getNameField(){
        return nameField;
    }
    public CreationPane() {
        super();
        playerLabel = new GameLabel("Player Name");
        this.getChildren().add(playerLabel);
        nameField = new TextField();
        nameField.setMaxWidth(200);
        nameField.setPromptText("Enter Hero Name...");
        nameField.setFocusTraversable(false);
        this.getChildren().add(nameField);

        this.playerLabel.translateXProperty().bind(this.widthProperty().subtract(this.playerLabel.widthProperty()).divide(2));
        this.nameField.translateXProperty().bind(this.widthProperty().subtract(this.nameField.widthProperty()).divide(2));

        this.playerLabel.setTranslateY((double) 650/2 - this.playerLabel.getHeight()*1.25);
        this.nameField.setTranslateY((double) 700/2);
        
        this.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        playerLabel.getStyleClass().addAll("game-font");
        
    }
    public void fadeIn(EventHandler<ActionEvent> finishedHandler) {
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(1500));
        ft.setNode(this);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);

        ft.setOnFinished(finishedHandler);

        ft.play();
    }
}
